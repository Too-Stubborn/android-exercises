package com.xuhj.kotlin.koans.convertions.invoke

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/6
 */
fun main(args: Array<String>) {
    val a= Invokable()
    println(a()()().numberOfInvocations)
}

class Invokable {
    var numberOfInvocations: Int = 0

    operator fun invoke(): Invokable {
        numberOfInvocations++
        return this
    }
}

fun invokeTwice(invokable: Invokable) = invokable()()

