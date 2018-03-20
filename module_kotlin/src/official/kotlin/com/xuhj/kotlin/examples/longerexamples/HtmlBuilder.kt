package com.xuhj.kotlin.examples.longerexamples

fun main(args: Array<String>) {
    val result =
            html {
                head {
                    title { +"XML encoding with Kotlin" }
                }
                body {
                    h1 { +"XML encoding with Kotlin" }
                    p { +"this format can be used as an alternative markup to XML" }

                    // an element with attributes and text content
                    a(href = "http://jetbrains.com/kotlin") { +"Kotlin" }

                    // mixed content
                    p {
                        +"This is some"
                        b { +"mixed" }
                        +"text. For more see the"
                        a(href = "http://jetbrains.com/kotlin") { +"Kotlin" }
                        +"project"
                    }
                    p { +"some text" }

                    // content generated from command-line arguments
                    p {
                        +"Command line arguments were:"
                        ul {
                            for (arg in args)
                                li { +arg }
                        }
                    }
                }
            }
    println(result)
}

@DslMarker
annotation class HtmlTagMarker

/**
 * com.xuhj.kotlin.Examples.LongerExamples.Element
 */
interface Element {
    fun render(sb: StringBuilder, indent: String)
}

/**
 * com.xuhj.kotlin.Examples.LongerExamples.TextElement
 */
class TextElement(val text: String) : Element {
    override fun render(sb: StringBuilder, indent: String) {
        sb.append("$indent$text\n")
    }
}

/**
 * com.xuhj.kotlin.Examples.LongerExamples.Tag
 */
@HtmlTagMarker
abstract class Tag(val name: String) : Element {
    val children = arrayListOf<Element>()
    val attributes = hashMapOf<String, String>()

    protected fun <T : Element> initTag(tag: T, init: T.() -> Unit): T {
        tag.init()
        children.add(tag)
        return tag
    }

    override fun render(sb: StringBuilder, indent: String) {
        sb.append("$indent<$name${renderAttributes()}>\n")
        for (child in children) {
            child.render(sb, indent + "  ")
        }
        sb.append("$indent</$name>\n")
    }

    fun renderAttributes(): String? {
        val sb = StringBuilder()
        for (key in attributes.keys) {
            sb.append("  $key=\"${attributes[key]}\"")
        }
        return sb.toString()
    }

    override fun toString(): String {
        val sb = StringBuilder()
        render(sb, "")
        return sb.toString()
    }
}

/**
 * com.xuhj.kotlin.Examples.LongerExamples.TagWithText
 */
abstract class TagWithText(name: String) : Tag(name) {
    operator fun String.unaryPlus() {
        children.add(TextElement(this))
    }
}

/**
 * Title
 */
class Title : TagWithText("title")

/**
 * com.xuhj.kotlin.Examples.LongerExamples.BodyTag
 */
abstract class BodyTag(name: String) : TagWithText(name) {
    fun b(init: B.() -> Unit) = initTag(B(), init)
    fun p(init: P.() -> Unit) = initTag(P(), init)
    fun h1(init: H1.() -> Unit) = initTag(H1(), init)
    fun ul(init: UL.() -> Unit) = initTag(UL(), init)
    fun a(href: String, init: A.() -> Unit) {
        val a = initTag(A(), init)
        a.href = href
    }
}

/**
 * Lable
 */

class B : BodyTag("b")

class LI : BodyTag("li")
class P : BodyTag("p")
class H1 : BodyTag("h1")
class A : BodyTag("a") {
    public var href: String
        get() = attributes["href"]!!
        set(value) {
            attributes["href"] = value
        }
}

/**
 * com.xuhj.kotlin.Examples.LongerExamples.UL
 */
class UL : BodyTag("ul") {
    fun li(init: LI.() -> Unit) = initTag(LI(), init)
}

/**
 * html
 */
fun html(init: HTML.() -> Unit): HTML {
    val html = HTML()
    html.init()
    return html
}

/**
 * com.xuhj.kotlin.Examples.LongerExamples.HTML
 */
class HTML : TagWithText("html") {
    fun head(init: Head.() -> Unit) = initTag(Head(), init)

    fun body(init: Body.() -> Unit) = initTag(Body(), init)
}

/**
 * com.xuhj.kotlin.Examples.LongerExamples.Head
 */
class Head : TagWithText("head") {
    fun title(init: Title.() -> Unit) = initTag(Title(), init)
}

/**
 * com.xuhj.kotlin.Examples.LongerExamples.Body
 */
class Body : BodyTag("body")
