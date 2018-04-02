package com.xuhj.kotlin.example11

/**
 * Kotlin 枚举类
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/8/19
 */
fun main(args: Array<String>) {
    test1()
}

// ------------------------------------------------------------------------------------------------
/**
 * 枚举类最基本的用法是实现一个类型安全的枚举。
 * 枚举常量用逗号分隔,每个枚举常量都是一个对象
 */
enum class Color {
    WHITE, BLACK, RED, GREEN, YELLOW, BLUE
}

// ------------------------------------------------------------------------------------------------
/**
 * 枚举初始化
 *
 * 每一个枚举都是枚举类的实例，它们可以被初始化
 */
enum class Color2(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF);
}

// 默认名称为枚举字符名，值从0开始。若需要指定值，则可以使用其构造函数
enum class Shape(value: Int) {
    oval(1),
    rectangle(2)
}

// 枚举还支持以声明自己的匿名类及相应的方法、以及覆盖基类的方法。
enum class ProtocolState {
    WAITTING {
        override fun signal() = TALKING
    },

    // 如果枚举类定义任何成员，要使用分号将成员定义中的枚举常量定义分隔开
    TALKING {
        override fun signal() = WAITTING
    };

    abstract fun signal(): ProtocolState
}

// ------------------------------------------------------------------------------------------------
/**
 * 使用枚举常量
 *
 * Kotlin 中的枚举类具有合成方法，允许遍历定义的枚举常量，并通过其名称获取枚举常数
 */
fun test1(): Unit {
    /*
        // 转换指定 name 为枚举值，若未匹配成功，会抛出IllegalArgumentException
        EnumClass.valueOf(value: String): EnumClass
        EnumClass.values(): Array<EnumClass>

        // 获取枚举相关信息
        val name: String //获取枚举名称
        val ordinal: Int //获取枚举值在所有枚举数组中定义的顺序
     */

    for (value in Color2.values()) {
        print(value.name)
    }
    println()
    var clr = Color2.RED
    println(Color2.valueOf("RED"))
    try {
        println(Color2.valueOf("red"))
    } catch(e: Exception) {
        println(e.message)
    }
    println(clr.name)
    println(clr.ordinal)

    printAllValues<Color2>()
}

// 自 Kotlin 1.1 起，可以使用 enumValues<T>() 和 enumValueOf<T>() 函数以泛型的方式访问枚举类中的常量
inline fun <reified T : Enum<T>> printAllValues() {
    print(enumValues<T>().joinToString { it.name })
}