package com.xuhj.kotlin

import org.junit.Test
import java.util.*


/**
 * Kotlin
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/7/5
 */
fun getList(): List<Int> {
    val arrayList = arrayListOf(1, 5, 2)
    Collections.sort(arrayList,{ x, y-> y-x})
    return arrayList
}
class KotlinTest {

    @Test
    fun main() {
//        test1()
//        test2(1, 3, 5, 7, 9)
//        test3()
//        test4()
//        test5()
//        test6("11")
//        test7()
//        test8()
//        test9()
//        test10()
//        test12()
//        test13()
//        test14()
//        test15()
//        test16()
//        test17()
        test18()
    }

    /**
     * 参数输入
     */
    fun test1(a: Int? = 1, b: Int? = 1): Int? {
        println("--- test1() ---")
//        if (a == null || b == null) {
//            return null
//        }
        println(a!! + b!!)

        return a + b

    }

    /**
     * for循环
     */
    fun test2(vararg args: Int) {
        println("--- test2() ---")
        for (temp in args) {
            print(temp)
        }
        println()
    }

    /**
     * lambda
     */
    fun test3() {
        println("--- test3() ---")
        val sumLambda: (Int, Int) -> Int = { x, y -> x + y }
        println(sumLambda(1, 2))  // 输出 3
    }

    /**
     * 字符串模板
     * $ 表示一个变量名或者变量值
     * $varName 表示变量值
     * ${varName.fun()} 表示变量的方法返回值:
     */
    fun test4() {
        println("--- test4() ---")
        var a = 1
        val s1 = "s1 is $a"
        println(s1)
        a = 2
        val s2 = "${s1.replace("is", "was")},but now is $a"
        println(s2)
    }

    /**
     * NULL检查机制
     */
    fun test5() {
        println("--- test5() ---")
        //类型后面加?表示可为空
        var age: String? = "23"
        //抛出空指针异常
        val ages = age!!.toInt()
        //不做处理返回 null
        val ages1 = age?.toInt()
        //age为空返回-1
        val ages2 = age?.toInt() ?: -1
    }

    /**
     *  类型检测及自动类型转换
     *  is 运算符检测一个表达式是否某类型的一个实例(类似于Java中的instanceof关键字)
     */
    fun test6(obj: Any): Int? {
        println("--- test6() ---")
        if (obj is String) {
            // 做过类型判断以后，obj会被系统自动转换为String类型
            return obj.length
        }

        //在这里还有一种方法，与Java中instanceof不同，使用!is
        if (obj !is String) {
            return null
        }

        // 在 `&&` 运算符的右侧, `obj` 的类型会被自动转换为 `String`
        if (obj is String && obj.length > 0)
            return obj.length

        // 这里的obj仍然是Any类型的引用
        return null
    }

    /**
     * 区间
     */
    fun test7() {
        println("--- test7() ---")

        // 输出“1234”
        for (i in 1..4) {
            print(i)
        }
        println()

        // 什么都不输出
        for (i in 4..1) {
            print(i)
        }
        println()

        // 输出“135”
        for (i in 1..6 step 2) {
            print(i)
        }
        println()

        // 输出"42"
        for (i in 4 downTo 1 step 2) {
            print(i)
        }
        println()

        // i in (1, 10) 排除了 10
        for (i in 1 until 10) {
            print(i)
        }
        println()

    }

    /**
     * 类型转换
     *
     * 由于不同的表示方式，较小类型并不是较大类型的子类型，较小的类型不能隐式转换为较大的类型。
     * 这意味着在不进行显式转换的情况下我们不能把 Byte 型值赋给一个 Int 变量。
     */
    fun test8() {
        val a: Byte = 1 // OK, 字面值是静态检测的
//        val b: Int = b // 错误

        var c: Short = a.toShort()
        var d: Int = a.toInt()
        var e: Long = a.toLong()
        var f: Float = a.toFloat()
        var g: Double = a.toDouble()
        var h: Char = a.toChar()
        var i: String = a.toString()

    }

    /**
     * 位操作符
     *
     * 对于Int和Long类型，还有一系列的位操作符可以使用
     */
    fun test9() {

        var a = 0x01
        a.shl(1) // 左移位 (Java’s <<)
        a.shr(1) // 右移位 (Java’s >>)
        a.ushr(1) // 无符号右移位 (Java’s >>>)
        a.and(0x11) // 与
        a.or(0x11) // 或
        a.xor(0x11) // 异或
        a.inv() // 反向
    }

    /**
     *
     */
    fun test10() {
        println("--- test10() ---")
        var a = listOf(1, 2, "3")
        var b = Array(3, { i -> (i * 1) })


        for (t in a) {
            print(t)
        }
        println()

        for (t in b) {
            print(t)
        }
        println()


    }

    /**
     *
     */
    fun test11() {
        var s: String = """1
                        |2
                        |3"
                        |4""".trimMargin()
        for (c in s) {
            print(c)
        }
        println("\nlength:" + s.length)

        println("expression is a = ${(1 + 1) * 4 / 3}")
    }

    /**
     * 对象比较
     */
    fun test12() {
        println("--- test12() ---")

        // 在范围是 [-128, 127] 之间并不会创建新的对象，注意是Int?
        var a1: Int = 127
        var a2: Int = 128

        var b1: Int? = a1
        var b2: Int? = a2

        println("b1===a1 : " + (b1 === a1))

        b1?.plus(2)
        println("a1:" + a1)

        println("b2===a2 : " + (b2 === a2))

        b2?.plus(2)
        println("a2:" + a2)
    }

    /**
     * IF表达式
     */
    fun test13() {
        println("--- test13() ---")
        // 与传统用法一样

        // 作为表达式
        val a = 1
        val b = 2

        val max = if (a > b) {
            a
        } else {
            b
        }
        println(max)
    }

    /**
     * 使用区间
     */
    fun test14() {
        println("--- test14() ---")
        val x = 5
        val min = 10
        val max = 100

        println((x in min..max))

        if (x !in min..max) {
            println("yes")
        } else {
            println("no")
        }
    }

    /**
     * When表达式
     */
    fun test15() {
        val x = 3
        // 用法与switch类似
        when (x) {
            1 -> println("x==1")
            2 -> println("x==2")
            else -> {
                println("else")
            }
        }

        // 替代if
        when {
            x == 1 -> {
                println("x==1")
            }
            x is Int -> {
                println("x is Int")
            }
            else -> {
                println("else")
            }
        }

        val items = arrayOf("apple", "banana", "tomato")
        when {
            "app" in items -> {
                println("has apple")
            }
            items.contains("bana") -> {
                println("contains banana")
            }
            items.contains("tomato") -> {
                println("has to")
            }
        }
        ""

    }

    /**
     * 循环控制
     */
    fun test16() {
        println("--- test16() ---")
        var arrays = arrayOf(2, 3, 5, 7, 11, 13)

        // method 1

        for (a in arrays) {
            println("value is $a")
        }

        println("--------------")
        // method2
        loop@ for (i in arrays.indices) {
            for (j in 0..i) {
                if (i == 3) {
                    println("continue 3")
                    continue@loop
                }
            }
            println("index is $i")
        }

        println("--------------")
        // method3
        for ((index, value) in arrays.withIndex()) {
            println("index=$index,value=$value")
        }

        println("--------------")
        // foreach
        arrays.forEach out@ {
            if (it == 0) return@out
            print(it)
        }

    }

    /**
     *
     */
    fun test17() {
        var user: User = User("xuhaojie", 25)
//        user.name = "xuhaojie"
        user.age = 28
        user.sex = 1
        user.address = "hangzhou"

        println("name is ${user.name}" +
                "\nage is ${user.age}" +
                "\nsex is ${user.sex}" +
                "\naddress is ${user.address}")
    }

    /**
     * 嵌套类
     */
    fun test18() {
        var outer = Outer()

        println("colro is ${outer.color}")

        outer.InnerNested().changeColor()
        println("color is changed to ${outer.color}")

        outer.setListener(object : OnClicklistener {
            override fun onClick() {
                println("onClick")
            }
        })
    }

}