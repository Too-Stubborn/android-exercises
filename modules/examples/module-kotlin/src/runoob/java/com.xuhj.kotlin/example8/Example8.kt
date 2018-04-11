package com.xuhj.kotlin.example8

import ex.ExUser
import ex.jump

/**
 * Kotlin 扩展
 * <p>
 * Kotlin 可以对一个类的属性和方法进行扩展，且不需要继承或使用 Decorator 模式。
 * 扩展是一种静态行为，对被扩展的类代码本身不会造成任何影响。
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/8/9
 */
fun main(args: Array<String>) {
    test2()
}

// ------------------------------------------------------------------------------------------------
class Object(var name: String)

/**
 * 扩展函数
 */
fun Object.addTest() {
    println("Object.addTest($name)")
}

/**
 * 对Int扩展，添加一个自定义函数
 * @return a+b
 */
fun Int.myplus(b: Int): Int {
    // this关键字指代接收者对象(receiver object)(也就是调用扩展函数时, 在点号之前指定的对象实例)
    return this.plus(b)
}

fun test1() {
    Object("xuhaojie").addTest()  // print"Object.addTest(xuhaojie)"

    var a: Int = 1
    println(a.myplus(1))  // print"2"
}

// ------------------------------------------------------------------------------------------------
/**
 * 扩展函数是静态解析的
 */
open class A {
    var name: String = "com.xuhj.kotlin.Examples.LongerExamples.A.name"
    fun age() = 10
}

class B : A()

fun A.foo() = "com.xuhj.kotlin.Examples.LongerExamples.A"  // 扩展函数
fun B.foo() = "com.xuhj.kotlin.Examples.LongerExamples.B"  // 扩展函数
fun A.age() = 20  // 扩展函数，若扩展函数和成员函数一致，则使用该函数时，会优先使用成员函数。
fun printFoo(a: A) {
    println(a.foo())
}

fun test2() {
    // 扩展函数是静态解析的，并不是接收者类型的虚拟成员，在调用扩展函数时，
    // 具体被调用的的是哪一个函数，由调用函数的的对象表达式来决定的，而不是动态的类型决定的
    printFoo(B())  // print "com.xuhj.kotlin.Examples.LongerExamples.A"
    // // 扩展函数，若扩展函数和成员函数一致，则使用该函数时，会优先使用成员函数。
    println(A().age())  // print "10"
}

// ------------------------------------------------------------------------------------------------
/**
 * 扩展一个空对象
 */
fun Any?.toString(): String {
    if (this == null) return "null"
    // 空检测之后，“this”会自动转换为非空类型，所以下面的 toString()
    // 解析为 Any 类的成员函数
    return toString()
}

/**
 * 除了函数，Kotlin 也支持属性对属性进行扩展
 */
val <T>List<T>.lastIndex: Int
    get() = 99

//val com.xuhj.kotlin.Examples.LongerExamples.A.name = "name"  // 错误：扩展属性不能有初始化器
// 扩展属性只能被声明为 val
val A.name // 正确写法
    get() = "Extend.name"

fun test3() {
    var s = null
    println(s.toString())

    var list = listOf(1, 2, 3, 4)
    println(list.lastIndex)

    var a = A()
    println(a.name)
}

// ------------------------------------------------------------------------------------------------
/**
 * 伴生对象的扩展
 *
 * 如果一个类定义有一个伴生对象 ，你也可以为伴生对象定义扩展函数和属性。
 * 伴生对象通过"类名."形式调用伴生对象，伴生对象声明的扩展函数，通过用类名限定符来调用
 */
class MyClass {
    companion object {} // 将被称为 "Companion"
}

fun MyClass.Companion.fly() {
    println("fly")
}

val MyClass.Companion.no: Int
    get() = 18

fun test4() {
    MyClass.fly()
    println("no is ${MyClass.no}")
}

// ------------------------------------------------------------------------------------------------
/**
 * 扩展的作用域
 *
 * 通常扩展函数或属性定义在顶级包下
 */
fun test5() {
// 要使用所定义包之外的一个扩展, 通过import导入扩展的函数名进行使用
    ExUser().jump()
}

// ------------------------------------------------------------------------------------------------
/**
 * 扩展声明为成员
 *
 * 在一个类内部你可以为另一个类声明扩展。
 * 在这个扩展中，有个多个隐含的接受者，其中扩展方法定义所在类的实例称为分发接受者，而扩展方法的目标类型的实例称为扩展接受者
 */
// 在 com.xuhj.kotlin.Examples.LongerExamples.B 类内，创建了 com.xuhj.kotlin.Examples.LongerExamples.A 类的扩展。
// 此时，com.xuhj.kotlin.Examples.LongerExamples.B 被成为分发接受者，而 com.xuhj.kotlin.Examples.LongerExamples.A 为扩展接受者。
// 从上例中，可以清楚的看到，在扩展函数中，可以调用派发接收者的成员函数
class ExtensionA {
    fun walk() {
        println("ExtensionA.walk")
    }
}

class ExtensionB {
    fun jump() {
        println("ExtensionB.jump")
    }

    fun walk() {
        println("ExtensionB.walk")
    }

    // 假如在调用某一个函数，而该函数在分发接受者和扩展接受者均存在，
    // 则以扩展接收者优先，要引用分发接收者的成员你可以使用限定的 this 语法
    fun ExtensionA.reWalk() {
        jump()
        walk()
        this@ExtensionB.walk()
    }

    fun caller(a: ExtensionA) {
        a.reWalk()  // // 调用扩展函数
    }
}

fun test6() {
    // print"ExtensionB.jump
    //    ExtensionA.walk
    //    ExtensionB.walk"
    ExtensionB().caller(ExtensionA())
}