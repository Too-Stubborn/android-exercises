package Temp

import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/8/24
 */
fun main(args: Array<String>) {

//    val (p1, p2) = MyClass("param1", "10")
//    println("p1 is $p1,p2 is $p2")
//
//    val (name, age) = MyUser("xuhaojie", 18)
//    println("name is $name,age is $age")

//    val a = 1 + 1L + 3
//    println(a is Long)

//    val a: Array<String?> = arrayOfNulls(4)
//    val ary = Array(3, { it })
//    for (i in ary) {
//        print(i)
//    }

//    var a: Int = 3
//    println(a in 1..3)
//    println(a in 0 until 3)
//    println(a in 3 downTo 0)
//    println(a in 10 downTo 3)
//    when (a) {
//        in 0..2 -> println("0..3")
//        in 0 until 3 -> println("0 until 3")
//        in 3 downTo 0 -> println("3 downTo 0")
//        in 3 downTo 0 -> println("3 downTo 0")
//    }
//    println(when (a) {
//        is Int -> a.toString()
//        else -> 0
//    })

//    val a = 1
//    val b = 2
//    val max = if (a > b) {
//        10
//        a
//    } else {
//        20
//        b
//    }
//    println(max)

//    val b: String? = null
//    val l = b!!.length
//    listOf(1,2,3).filterNotNull()

//    val y: String? = null
//    val x: String = y as String
//    println(x)
//    val a = 1


    val oddLength = compose(::isOdd, ::length)
    val strings = listOf("a", "ab", "abc")
    println(strings.filter { it.length == 3 })
}

fun test2() {
}

val <T>Array<T>.isEmpty: Boolean
    get() = size == 0

fun isOdd(x: Int) = x % 2 == 0
fun length(s: String) = s.length

fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C {
    return { a: A -> f(g(a)) }
}

class MyClass(val param1: String, val param2: String) {

    operator fun component1(): String {
        return param1
    }

    operator fun component2(): String {
        return param2
    }

}

data class MyUser(var name: String, val age: Int) : Iterator<String> {
    override fun hasNext(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun next(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

open class Base {
    open fun v() {}
    fun nv() {

    }
}

class Derived : Base() {
    var a: String by Delegate()
    override fun v() {
        super.nv()
    }
}

class Delegate : ReadWriteProperty<Any?, String> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to ${property.name} in $thisRef")
    }
}

fun test1() {
    var derived = Derived()
    val a: String  by lazy { "la" }
    val b: String  by Delegates.observable("") {
        property, oldValue, newValue ->
        println(property)
    }
    val c: String by Delegates.vetoable("") {
        property, oldValue, newValue ->
        println(property)
        false
    }
    val d: String by Delegates.notNull()

    println(derived.a)
    derived.a = "NEW"
}

