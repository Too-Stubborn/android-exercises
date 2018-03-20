package com.xuhj.kotlin.example2

/**
 * Kotlin 基本数据类型
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/8/3
 */
fun main(args: Array<String>) {
    test8()
}

// ------------------------------------------------------------------------------------------------
/**
 * 字面常量
 *
 * 十进制：123
 * 长整型以大写的 L 结尾：123L
 * 16 进制以 0x 开头：0x0F
 * 2 进制以 0b 开头：0b00001011
 * 注意：8进制不支持
 */
fun test0() {
    val oneMillion = 1_000_000
    val creditCardNumber = 1234_5678_9012_3456L
    val socialSecurityNumber = 999_99_9999L
    val hexBytes = 0xFF_EC_DE_5E
    val bytes = 0b11010010_01101001_10010100_10010010
}

// ------------------------------------------------------------------------------------------------
/**
 * 比较两个数字
 *
 * Kotlin 中没有基础数据类型，只有封装的数字类型，你每定义的一个变量，
 * 其实 Kotlin 帮你封装了一个对象，这样可以保证不会出现空指针。数字类型也一样，
 * 所有在比较两个数字的时候，就有比较数据大小和比较两个对象是否相同的区别了。
 * 在 Kotlin 中，三个等号 === 表示比较对象地址，两个 == 表示比较两个值大小。
 */
fun test1() {
    val a: Int = 10000
    println(a === a)  // true，值相等，对象地址相等
    //经过了装箱，创建了两个不同的对象
    val boxA: Int? = a
    val boxB: Int? = a
    println(boxA === boxB)  //  false，值相等，对象地址不一样
    println(boxA == boxB)  // true，值相等
}

// ------------------------------------------------------------------------------------------------
/**
 * 类型转换
 *
 * 由于不同的表示方式，较小类型并不是较大类型的子类型，较小的类型不能隐式转换为较大的类型。
 * 这意味着在不进行显式转换的情况下我们不能把 Byte 型值赋给一个 Int 变量。
 */
fun test2() {
    val a: Byte = 1 // OK, 字面值是静态检测的
//        val b: Int = b // 会编译错误
    // 可以用如下方法进行转换
    var c: Short = a.toShort()
    var d: Int = a.toInt()
    var e: Long = a.toLong()
    var f: Float = a.toFloat()
    var g: Double = a.toDouble()
    var h: Char = a.toChar()
    var i: String = a.toString()
    // 有些情况下也是可以使用自动类型转化的，
    // 前提是可以根据上下文环境推断出正确的数据类型而且数学操作符会做相应的重载
    val j = 1L + 3 // Long + Int => Long
}

// ------------------------------------------------------------------------------------------------
/**
 * 位操作符
 *
 * 对于Int和Long类型，还有一系列的位操作符可以使用
 */
fun test3() {
    var a = 0b0010  // 2
    println("a is $a")

    println(a.shl(1))  // 左移位 (Java’s <<)
    println(a.shr(1))  // 右移位 (Java’s >>)
    println(a.ushr(1))  // 无符号右移位 (Java’s >>>)
    println(a.and(0b1111))   // 与
    println(a.or(0b1111))  // 或
    println(a.xor(0b1111))   // 异或
    println(a.inv())  // 反向
    println(a.inc())  // 不知道
}

// ------------------------------------------------------------------------------------------------
/**
 * 字符
 *
 * 和 Java 不一样，Kotlin 中的 Char 不能直接和数字操作，Char 必需是单引号''包含起来的。比如普通字符 '0'，'a'。
 *
 * 字符字面值用单引号括起来: '1'。 特殊字符可以用反斜杠转义。 支持这几个转义序列：\t、 \b、\n、\r、\'、\"、\\ 和 \$。
 * 编码其他字符要用 Unicode 转义序列语法：'\uFF00'
 */
fun test4() {
    fun temp(a: Char) {
        if (a == '1') {
            print(a)
        }
    }
    temp('1')

    fun decimalValue(b: Char): Int {
        if (b !in '0'..'9') {
            throw IllegalArgumentException("out of range")
        }
        // 显式转换为数字
        return b.toInt() - '0'.toInt()
    }
    println(decimalValue('7'))
}

// ------------------------------------------------------------------------------------------------
/**
 * 布尔
 *
 * 布尔用 Boolean 类型表示，它有两个值：true 和 false。
 * 若需要可空引用布尔会被装箱。
 * || – 短路逻辑或
 * && – 短路逻辑与
 * ! - 逻辑非
 *
 */
fun test5() {
    var a = true
    var b = false
    println(a || b)
    println(a && b)
    println(!a)
}

// ------------------------------------------------------------------------------------------------
/**
 * 数组
 *
 * 数组用类 Array 实现，并且还有一个 size 属性及 get 和 set 方法，由于使用 [] 重载了 get 和 set 方法，
 * 所以我们可以通过下标很方便的获取或者设置数组对应位置的值。
 *
 */
fun test6() {
    var a = arrayOf(1, 2, 3)  // [1,2,3]
    var b = Array(3, { i -> (i * 2) })  // [0,2,4]

    for (i in a) {
        print(i)  // print 123
    }

    println()
    for (i in b) {
        print(i)  // print 024
    }
    println()
    //  注意: 与 Java 不同的是，Kotlin 中数组是不型变的（invariant）。
    //  除了类Array，还有ByteArray, ShortArray, IntArray，用来表示各个类型的数组，
    // 省去了装箱操作，因此效率更高，其用法同Array一样
    var x: IntArray = intArrayOf(1, 2, 3)
    x[0] = x[1] + x[2]
    println("x[0] is ${x[0]}")
}

// ------------------------------------------------------------------------------------------------
/**
 * 字符串
 *
 *
 */
fun test7() {
    // 和 Java 一样，String 是可不变的。方括号 [] 语法可以很方便的获取字符串中的某个字符，
    // 也可以通过 for 循环来遍历
    var a = "asd123456"
    for (i in a) {
        print(i)  // print asd123456
    }
    println()

    // Kotlin 支持三个引号 """ 扩起来的字符串，支持多行字符串
    val t1 = """开始
    第一行
    第二行
    结束"""
    println(t1)  // 输出有一些前置空格

    // String 可以通过 trimMargin() 方法来删除多余的空白
    // 默认用'|'符号作边界前缀，但你可以选择其他字符并作为参数传入，比如 trimMargin(":")
    val t2 = """开始
    :第一行
    :第二行
    :结束""".trimMargin(":")
    println(t2)  // 前置空格删除了
}

// ------------------------------------------------------------------------------------------------
/**
 * 字符串模板
 */
fun test8() {
    // 字符串可以包含模板表达式 ，即一些小段代码，会求值并把结果合并到字符串中。 模板表达式以美元符（$）开头
    val a = 10
    val s1 = "a is $a"
    println(s1)  // print"a is 10"

    // 用花括号扩起来的任意表达式
    val s2 = "a is ${a + 1}"
    println(s2)  // print"s2 is 11"

    // 原生字符串和转义字符串内部都支持模板。 如果你需要在原生字符串中表示字面值 $ 字符（它不支持反斜杠转义）
    val price = "${'$'}9.99"
    println(price)  // print"$9.99"

}

// ------------------------------------------------------------------------------------------------
/**
 * 对象比较
 */
fun test9() {
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