package com.xuhj.kotlin.example10

/**
 * Kotlin 泛型
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/8/18
 */
fun main(args: Array<String>) {
    test3()
}

// ------------------------------------------------------------------------------------------------
/**
 * 泛型，即 "参数化类型"，将类型参数化，可以用在类，接口，方法上。
 * 与 Java 一样，Kotlin 也提供泛型，为类型安全提供保证，消除类型强转的烦恼。
 */
class Box<T>(t: T) {
    var value = t
}

// Kotlin 泛型函数的声明与 Java 相同，类型参数要放在函数名的前面
fun <T> doPrintGenerics(t: T) = when (t) {
    is Int -> println("is Int = $t")
    is String -> println("is String = $t")
    else -> println("no Int or String ")
}

fun test1() {
    // 编译器会进行类型推断，2 类型 Int，所以编译器知道我们说的是 Box<Int>(2)。
    val box: Box<Int> = Box(2)
    println("box Int is ${box.value}")  // box Int is 2
    val box2: Box<String> = Box("box2")
    println("box String is ${box2.value}")  // box String is box2

    doPrintGenerics(123)  // is Int = 123
    doPrintGenerics("sss")  // is String = sss
    doPrintGenerics(0.00)  // no Int or String
}

// ------------------------------------------------------------------------------------------------
/**
 * 泛型约束
 *
 * 我们可以使用泛型约束来设定一个给定参数允许使用的类型。
 * Kotlin 中使用 : 对泛型的的类型上限进行约束。
 * 最常见的约束是上界(upper bound)
 */
// Comparable 的子类型可以替代 T
fun <T : Comparable<T>> mySort(list: List<T>) {
    for (t in list) {
        print(t)
    }
    println()

}

// 对于多个上界约束条件，可以用 where 子句
//fun <T> cloneWhenGreater(list: List<T>, threshold: T): List<T>
//        where T : Comparable, Cloneable {
//    return list.filter(it > threshold).map(it.clone())
//}

fun test2() {
    // OK。Int 是 Comparable<Int> 的子类型
    mySort(listOf(1, 2, 3))  // print "123"
    // 错误：HashMap<Int, String> 不是 Comparable<HashMap<Int, String>> 的子类型
//    mySort(listOf(HashMap<String, String>()))
}

// ------------------------------------------------------------------------------------------------
/**
 * 型变
 *
 * Kotlin 中没有通配符类型，
 * 它有两个其他的东西：声明处型变（declaration-site variance）与类型投影（type projections）。
 */

/**
 * 声明处型变
 *
 * 声明处的类型变异使用协变注解修饰符：in、out，消费者 in, 生产者 out。
 *
 */
// 使用 out 使得一个类型参数协变，协变类型参数只能用作输出，可以作为返回值类型但是无法作为入参的类型：
// 定义一个支持协变的类
class RunoobA<out A>(val a: A) {
    fun foo(): A {
        return a
    }
}

// in 使得一个类型参数逆变，逆变类型参数只能用作输入，可以作为入参的类型但是无法作为返回值的类型:
// 定义一个支持逆变的类
class RunoobB<in A>(a: A) {
    fun foo(a: A) {
    }
}

fun test3() {
    var oobStr: RunoobA<String> = RunoobA("a")
    var oobAny: RunoobA<Any> = RunoobA<Any>("b")
    oobAny = oobStr
    println(oobAny.foo())  // print "a"

    var oobStr2 = RunoobB("a2")
    var oobAny2 = RunoobB<Any>("b2")
    oobStr2 = oobAny2
}

// ------------------------------------------------------------------------------------------------
/**
 * 星号投射
 *
 * 有些时候, 你可能想表示你并不知道类型参数的任何信息, 但是仍然希望能够安全地使用它.
 * 这里所谓"安全地使用"是指, 对泛型类型定义一个类型投射, 要求这个泛型类型的所有的实体实例, 都是这个投射的子类型。
 *<p>
 * 对于这个问题, Kotlin 提供了一种语法, 称为 星号投射(star-projection):
 * 假如类型定义为 Foo<out T> , 其中 T 是一个协变的类型参数, 上界(upper bound)为 TUpper ,Foo<> 等价于 Foo<out TUpper> .
 * 它表示, 当 T 未知时, 你可以安全地从 Foo<> 中 读取TUpper 类型的值.
 * 假如类型定义为 Foo<in T> , 其中 T 是一个反向协变的类型参数, Foo<> 等价于 Foo<inNothing> .
 * 它表示, 当 T 未知时, 你不能安全地向 Foo<> 写入 任何东西.
 * 假如类型定义为 Foo<T> , 其中 T 是一个协变的类型参数, 上界(upper bound)为 TUpper ,
 * 对于读取值的场合, Foo<*> 等价于 Foo<out TUpper> , 对于写入值的场合, 等价于 Foo<in Nothing> .
 *<p>
 * 如果一个泛型类型中存在多个类型参数, 那么每个类型参数都可以单独的投射.
 * 比如, 如果类型定义为interface Function<in T, out U> , 那么可以出现以下几种星号投射:
 * Function<*, String> , 代表 Function<in Nothing, String> ;
 * Function<Int, *> , 代表 Function<Int, out Any?> ;
 * Function<, > , 代表 Function<in Nothing, out Any?> .
 * 注意: 星号投射与 Java 的原生类型(raw type)非常类似, 但可以安全使用
 */
fun <T> test4(t: T) {

}