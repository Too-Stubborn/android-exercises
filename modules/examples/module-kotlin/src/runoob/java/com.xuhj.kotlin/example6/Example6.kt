package com.xuhj.kotlin.example6

/**
 * Kotlin 继承
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/8/8
 */
fun main(args: Array<String>) {
    test3()
}

// ------------------------------------------------------------------------------------------------
/**
 * Kotlin 中所有类都继承该 Any 类，它是所有类的超类，对于没有超类型声明的类是默认超类
 *
 * Any 默认提供了三个函数：
 * equals()
 * hashCode()
 * toString()
 *
 * 注意：Any 不是 java.lang.Object。
 */
class Empty  // 从 Any 隐式继承

// 如果一个类要被继承，可以使用 open 关键字进行修饰。
open class Base  // 定义基类

class Request : Base()  // 继承Base

// ------------------------------------------------------------------------------------------------
/**
 * 构造函数
 *
 *
 */
open class Person(var name: String, var age: Int) {  // 基类
    init {
        println("基类初始化")
    }
}

/**
 * 子类有主构造函数
 *
 * 如果子类有主构造函数， 则基类必须在主构造函数中立即初始化。
 */
class Student(name: String, age: Int, var number: String) : Person(name, age)

/**
 * 子类没有主构造函数
 *
 * 如果子类没有主构造函数，则必须在每一个二级构造函数中用 super 关键字初始化基类，
 * 或者在代理另一个构造函数。初始化基类时，可以调用基类的不同构造方法。
 */
class Student2 : Person {
    constructor() : this("xuhaojie", 18)
    constructor(name: String, age: Int) : super(name, age) {
        println("子类初始化")
    }
}

fun test1() {
    var obj = Student("xhj", 18, "2017001")
    println("name is ${obj.name}" +
            "\nage is ${obj.age}" +
            "\nnumber is ${obj.number}")

    var obj2 = Student2("xhj", 18)
}

// ------------------------------------------------------------------------------------------------
/**
 * 重写
 */
open class Person2 {  // 基类
    open fun study() {  // 允许子类重写
        println("Person2.study")
    }

    open fun common() {
        println("Person2.common")
    }
}

interface Subject {  //接口的成员变量默认是 open 的
    fun math() {
        println("Subject.math")
    }

    fun common() {
        println("Subject.common")
    }
}

class Student3 : Person2(), Subject {
    override fun study() {  // 重写方法
        super.study()
    }

    override fun math() {
        super<Person2>.study()
        super<Subject>.math()
    }

    override fun common() {
        super<Person2>.common()
        super<Subject>.common()
    }
}

fun test2() {
    Student3().study()
    Student3().math()
    Student3().common()
}

// ------------------------------------------------------------------------------------------------
/**
 * 属性重写
 */
open class Person3 {
    open var name: String = "Person3"
    open var age: Int = 16
}

/**
 * 属性重写使用 override 关键字，属性必须具有兼容类型，每一个声明的属性都可以通过初始化程序或者getter方法被重写
 */
class Student4 : Person3() {
    override var name: String
        get() = super.name.plus("_001")
        set(value) {}
    override var age: Int
        get() = super.age
        set(value) {}
}

fun test3() {
    val std = Student4()
    println(std.name)
}