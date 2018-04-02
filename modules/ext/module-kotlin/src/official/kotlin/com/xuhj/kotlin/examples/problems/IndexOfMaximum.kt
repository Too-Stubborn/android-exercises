package com.xuhj.kotlin.examples.problems

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/4
 */
fun indexOfMax(a: IntArray): Int? {
    if (a.isEmpty()) return null
    var maxIndex = 0
    var maxValue = 0
    for ((index, value) in a.withIndex()) {
        if (index == 0) {
            maxIndex = 0
            maxValue = value
            continue
        }
        if (maxValue <= value) {
            maxIndex = index
            maxValue = value
        }
    }
    return maxIndex
}
