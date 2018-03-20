package com.xuhj.kotlin.example3

/**
 * Kotlin 条件控制
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/8/4
 */

fun main(args: Array<String>) {
    test3()
}

// ------------------------------------------------------------------------------------------------
/**
 * if基本表达式
 */
fun test1() {
    val a: Int = 1
    val b: Int = 2
    var max: Int = 0
    // 传统用法
    if (a < b) max = b

    // 使用 else
    if (a > b) {
        max = a
    } else {
        max = b
    }

    // 作为表达式，可以把if表达式的结果赋值给一个变量，可以代替java中的三元操作符
    max = if (a > b) a else b
}

// ------------------------------------------------------------------------------------------------
/**
 * 使用区间
 *
 * 使用 in 运算符来检测某个数字是否在指定区间内，区间格式为 x..y ：
 */
fun test2() {
    var x = 5
    if (x in 1..9) {
        print("x在区间内")
    }

}

// ------------------------------------------------------------------------------------------------
/**
 * When 表达式
 *
 * when 将它的参数和所有的分支条件顺序比较，直到某个分支满足条件。
 * when 既可以被当做表达式使用也可以被当做语句使用。如果它被当做表达式，符合条件的分支的值就是整个表达式的值，
 * 如果当做语句使用， 则忽略个别分支的值。
 *
 */
fun test3() {
    var x = 10
    // when 类似其他语言的 switch 操作符。
    when (x) {
        1 ->
            println("x = 1")
        2 ->
            println("x = 2")
        3, 4 ->
            // 可以把多个分支条件放在一起，用逗号分隔
            println("x = 3,4")
        in 1..10 ->
            // 可以检测一个值在（in）或者不在（!in）一个区间或者集合中
            println("x in 1..10")
        is Int ->
            // 检测一个值是（is）或者不是（!is）一个特定类型的值。
            // 注意： 由于智能转换，你可以访问该类型的方法和属性而无需 任何额外的检测
            println(x.toString())
        else ->
            // else 同 switch 的 default
            println("otherwise")
    }

    // when 也可以用来取代 if-else if链。 如果不提供参数，所有的分支条件都是简单的布尔表达式，
    // 而当一个分支的条件为真时则执行该分支
    when {
        x > 0 ->
            println("x > 1")
        x > 100 ->
            println("x > 100")
        else ->
            println("otherwise")
    }

    // 使用 in 运算符来判断集合内是否包含某实例
    val items = arrayOf("one", "two", "three")
    when {
        "on" in items ->
            println("one")
        items.contains("tw") ->
            println("contains tw")
        items.contains("three") ->
            println("contains three")
    }


}