package com.xuhj.kotlin.examples.problems

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/4
 */

fun sum(a: IntArray): Int {
    // Write your solution here
    var sum = 0
    for (i in a) {
        sum += i
    }
    return sum
}