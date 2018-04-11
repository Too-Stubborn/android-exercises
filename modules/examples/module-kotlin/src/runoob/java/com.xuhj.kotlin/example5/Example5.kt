package com.xuhj.kotlin.example5

/**
 * Kotlin 类和对象
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/8/7
 */
fun main(args: Array<String>) {
    test5()
}

// ------------------------------------------------------------------------------------------------
/**
 * 类定义
 *
 * Kotlin 类可以包含：构造函数和初始化代码块、函数、属性、内部类、对象声明。
 * Kotlin 中使用关键字 class 声明类，后面紧跟类名
 */
class MyObject1 {
    // 大括号内是类体构成

    // 可以在类中定义成员函数
    fun fire() {
        print("fire")
    }
}

// 我们也可以定义一个空类
class EmptyObject

// ------------------------------------------------------------------------------------------------
/**
 * 类的属性
 *
 * 属性定义
 */
class MyObject2 {
    /**
     * getter 和 setter 都是可选
     */
    // 如果属性类型可以从初始化语句或者类的成员函数中推断出来，那就可以省去类型，
    // val不允许设置setter函数，因为它是只读的
//    var allByDefault: Int? // 错误: 需要一个初始化语句, 默认实现了 getter 和 setter 方法
//    var initialized = 1    // 类型为 Int, 默认实现了 getter 和 setter
//    val simple: Int?       // 类型为 Int ，默认实现 getter ，但必须在构造函数中初始化
//    val inferredType = 1   // 类型为 Int 类型,默认实现 getter

    // 类的属性可以用关键字 var 声明为可变的，否则使用只读关键字 val 声明为不可变。
    var name: String = "xuhaojie"
        get() = field.toUpperCase()  // 将变量赋值后转换为大写
        set

    // Kotlin 中类不能有字段。提供了 Backing Fields(后端变量) 机制,
    // 备用字段使用field关键字声明,field 关键词只能用于属性的访问器
    var age: Int = 18
        get() = field  // 后端变量
        set(value) {
            if (value > 18) {
                field = value
            } else {
                field = 18
            }
        }
    var mobile: String = "110"
        get() {
            return "+86-" + field
        }
    var address: String = "China"
        private set  // 屏蔽set方法

}

fun test1() {
    // 我们可以像使用普通函数那样使用构造函数创建类实例
    var obj = MyObject2()  // Kotlin 中没有 new 关键字
    // 要使用一个属性，只要用名称引用它即可
    println("name is ${obj.name}")
    obj.age = 10
    println("age is ${obj.age}")
    obj.mobile = "10086"
    println("mobile is ${obj.mobile}")
    println("address is ${obj.address}")
}

// ------------------------------------------------------------------------------------------------
/**
 * 主构造器
 */
class MyObject3 constructor(name: String) {
    // 主构造器中不能包含任何代码，初始化代码可以放在初始化代码段中，
    // 初始化代码段使用 init 关键字作为前缀。
    init {
        println("初始化name is $name")
    }

    /**
     * 次构造函数
     */
    // 类也可以有二级构造函数，需要加前缀 constructor:
    // 如果类有主构造函数，每个次构造函数都要，或直接或间接通过另一个次构造函数代理主构造函数。
    // 在同一个类中代理另一个构造函数使用 this 关键字
    constructor(url: String, name: String) : this(name) {

    }

    var url: String = "www.baidu.com"
    var siteName = name

    fun internalTest() {
        println("内部函数")
    }
}

// 如果一个非抽象类没有声明构造函数(主构造函数或次构造函数)，它会产生一个没有参数的构造函数。
// 构造函数是 public 。如果你不想你的类有公共的构造函数，你就得声明一个空的主构造函数
class DontCreateMe private constructor() {
}

fun test2() {
    var obj = MyObject3("www.imnono.com", "Nono")
    println("siteName is ${obj.siteName}")
    obj.internalTest()
}

// ------------------------------------------------------------------------------------------------
/**
 * 抽象类
 *
 * 抽象是面向对象编程的特征之一，类本身，或类中的部分成员，都可以声明为abstract的。抽象成员在类中不存在具体的实现
 * 注意：无需对抽象类或抽象成员标注open注解。
 */
open class AbsBase {

    open fun one() {
        println("MyObject4.one()")
    }
}

abstract class AbsObject1 : AbsBase() {
    override fun one() {
        super.one()
    }
}

// ------------------------------------------------------------------------------------------------
/**
 * 嵌套类
 */
class Outer1 {  // 外部类

    class Nested {  // 嵌套类
        fun nestedTest() = 999
    }
}

fun test3() {
    var x = Outer1.Nested().nestedTest()  // 调用格式：外部类.嵌套类.嵌套类方法/属性
    println(x)
}

// ------------------------------------------------------------------------------------------------
/**
 * 内部类
 *
 * 内部类使用 inner 关键字来表示。
 * 内部类会带有一个对外部类的对象的引用，所以内部类可以访问外部类成员属性和成员函数。
 */
class Outer2 {
    val a: Int = 1
    val s: String = "外部类属性"

    /**
     * 嵌套内部类
     */
    inner class Inner {
        fun innerTest1() = a  // 访问外部类成员

        fun innerTest2() {
            val o = this@Outer2  // 获取外部类的成员变量
            println("内部类可以引用外部类的成员，例如：${o.s}")
        }
    }
}

fun test4() {
    val inner = Outer2().Inner()
    println(inner.innerTest1())
    inner.innerTest2()

}

// ------------------------------------------------------------------------------------------------
/**
 * 匿名内部类
 */
class MyObject4 {
    var a = "成员属性"
    fun setInterface(l: Listener) {
        l.onClick()
    }
}

interface Listener {
    fun onClick()
}

fun test5() {
    var obj = MyObject4()
    // 采用对象表达式来创建接口对象，即匿名内部类的实例。
    obj.setInterface(object : Listener {
        override fun onClick() {
            println("对象表达式创建匿名内部类的实例")
        }
    })
}

// ------------------------------------------------------------------------------------------------
/**
 * 类的修饰符
 *
 * 类的修饰符包括 classModifier 和_accessModifier
 */
class MyObject5 {
    // classModifier: 类属性修饰符，标示类本身特性。
//    abstract    // 抽象类
//    final       // 类不可继承，默认属性
//    enum        // 枚举类
//    open        // 类可继承，类默认是final的
//    annotation  // 注解类

    // accessModifier: 访问权限修饰符
//    private    // 仅在同一个文件中可见
//    protected  // 同一个文件中或子类可见
//    public     // 所有调用的地方都可见
//    internal   // 同一个模块中可见
}
