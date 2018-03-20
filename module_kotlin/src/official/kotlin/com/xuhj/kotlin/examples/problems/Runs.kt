package com.xuhj.kotlin.examples.problems

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/4
 */
fun runs(a: IntArray): Int {
    var runs = 0
    for (i in a.indices) {
        if (i == 0) {
            runs++
            continue
        }
        if (a[i] != a[i - 1]) {
            runs++
        }
    }
    return runs
}