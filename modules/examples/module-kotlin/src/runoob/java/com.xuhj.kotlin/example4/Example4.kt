package com.xuhj.kotlin.example4

/**
 * Kotlin 循环控制
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/8/5
 */
fun main(args: Array<String>) {
    test5()
}

// ------------------------------------------------------------------------------------------------
/**
 * For 循环
 */
fun test1() {
    var items = arrayOf("com.xuhj.kotlin.Examples.LongerExamples.A", "com.xuhj.kotlin.Examples.LongerExamples.B", "C", "D")
    // for 循环可以对任何提供迭代器（iterator）的对象进行遍历
    for (i in items) {
        print(i)
    }
    println()

    // 通过索引遍历一个数组或者一个 list
    // 注意这种"在区间上遍历"会编译成优化的实现而不会创建额外对象
    for (i in items.indices) {
        print(items[i])
    }
    println()

    // 可以用库函数 withIndex
    for ((index, value) in items.withIndex()) {
        println("index=$index, value=$value")
    }
}

// ------------------------------------------------------------------------------------------------
/**
 * while 与 do...while 循环
 *
 * do…while 循环和 while 循环相似，不同的是，do…while 循环至少会执行一次
 */
fun test2() {
    var x = 0
    do {
        x++
    } while (x < 0)
    println(x)  // print"1"
}

// ------------------------------------------------------------------------------------------------
/**
 * 返回和跳转
 *
 * Kotlin 有三种结构化跳转表达式：
 * return。默认从最直接包围它的函数或者匿名函数返回。
 * break。终止最直接包围它的循环。
 * continue。继续下一次最直接包围它的循环。
 */
fun test3() {
    for (i in 1..10) {
        if (i == 2) continue
        if (i > 8) break
        if (i > 5) return
        print(i)  // print"1345"
    }
}

// ------------------------------------------------------------------------------------------------
/**
 * Break 和 Continue 标签
 *
 * 在 Kotlin 中任何表达式都可以用标签（label）来标记。
 * 标签的格式为标识符后跟 @ 符号，例如：abc@、fooBar@都是有效的标签。
 * 要为一个表达式加标签，我们只要在其前加标签即可。
 */
fun test4() {
    labelA@ for (i in 1..5) {
        for (j in 1..5) {
            if (i > 2) break@labelA
            println("i=$i, j=$j")
        }
    }
}

// ------------------------------------------------------------------------------------------------
/**
 * 标签处返回
 * Kotlin 有函数字面量、局部函数和对象表达式。因此 Kotlin 的函数可以被嵌套。
 * 标签限制的 return 允许我们从外层函数返回。 最重要的一个用途就是从 lambda 表达式中返回。
 */
fun test5() {
    var args = arrayOf(1, 2, 3, 4)
    // 加标签并用以限制 return
    args.forEach labelA@ {
        if (it == 2) return@labelA
        print(it)  // print"134"
    }
    println()
    // 通常情况下使用隐式标签更方便。
    args.forEach {
        if (it == 2) return@forEach
        print(it)  // print"134"
    }
    println()
    // 或者，我们用一个匿名函数替代 lambda 表达式。 匿名函数内部的 return 语句将从该匿名函数自身返回
    fun t3() {
        args.forEach {
            fun(value: Int) {
                if (value == 2) return
                print(value)
            }
        }
    }
//    t3()
    println("end")
}