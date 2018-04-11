package com.xuhj.kotlin.koans.builders.functionapply

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/7
 */
fun <T> T.myApply(f: T.() -> Unit): T {
    this.f()
    return this
}

fun createString(): String {
    return StringBuilder().myApply {
        append("Numbers: ")
        for (i in 1..10) {
            append(i)
        }
    }.toString()
}

fun createMap(): Map<Int, String> {
    return hashMapOf<Int, String>().myApply {
        put(0, "0")
        for (i in 1..10) {
            put(i, "$i")
        }
    }
}