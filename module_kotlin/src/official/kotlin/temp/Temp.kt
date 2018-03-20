package temp

import java.util.Arrays.asList

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/8/30
 */
fun main(args: Array<String>) {

}

// ------------------------------------------------------------------------------------------------

fun html(init: HTML.() -> Unit) {

}

class HTML {
    val url: String = ""
    fun head() {

    }
}


fun <T, R> List<T>.map(tf: (T) -> R): List<R> {
    val result = arrayListOf<R>()
    for (item in this) {
        result.add(tf(item))
    }
    return result
}


fun lambdaTest(lmd: () -> Int): Int {
    return lmd()
}

fun format(a: String = "a", b: String, c: String) {
    println("$a+$b+$c")
}

fun varargs(vararg args: Int) {
    for (i in args.indices) {
        print(args.get(i))
    }
    println()
    for (i in args.indices) {
        print(args[i])
    }
}

fun spread() {
    val a = arrayOf(1, 2, 3)
    val b = asList(0, *a, 4, 5, 6)
    for (v in b) {
        print(v)
    }

    println()
    val a2 = 22
    println(a2 fuck 1)
}

tailrec infix inline fun Int.fuck(x: Int) = this - x
