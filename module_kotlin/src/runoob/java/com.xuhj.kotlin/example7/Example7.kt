package com.xuhj.kotlin.example7

/**
 * Kotlin 接口
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/8/9
 */
fun main(args: Array<String>) {
    test1()
}

// ------------------------------------------------------------------------------------------------
/**
 * Kotlin 接口与 Java 8 类似，使用 interface 关键字定义接口，允许方法有默认实现
 */
interface MyInterface {
    fun one() {
        println("MyInterface.one")
    }
}

/**
 * 接口中的属性
 */
interface MyInterface2 {
    // 接口中的属性只能是抽象的，不允许初始化值，接口不会保存属性值，实现接口时，必须重写属性
    var cellphone: String   // 属性, 抽象的

    fun two() {
        println("MyInterface2.two")
    }
}

/**
 * 实现接口
 *
 * 个类或者对象可以实现一个或多个接口。
 */
class MyObjectImpl : MyInterface, MyInterface2 {
    override var cellphone: String = "override cellphone"  //重载属性

    /**
     * 函数重写
     */
    override fun one() {
        println("MyObjectImpl.one")
    }
}

fun test1() {
    MyObjectImpl().one()
}