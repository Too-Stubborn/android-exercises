package com.xuhj.kotlin.examples.problems

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/4
 */
fun findPairless(a: IntArray): Int {
    var status = BooleanArray(a.size)
    outer@ for ((i, v) in a.withIndex()) {
        if (status[i]) {
            continue@outer
        }
        for ((ci, cv) in a.withIndex()) {
            if (i == ci) {
                continue
            }
            if (v == cv) {
                status[i] = true
                status[ci] = true
                continue@outer
            }
        }
        return v
    }
    return 0
}

fun main(args: Array<String>) {
    println(findPairless(intArrayOf(0, 1, 0)))
}
