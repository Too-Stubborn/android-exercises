package com.xuhj.kotlin.examples.problems

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/4
 */
fun isPalindrome(s: String): Boolean {
    for (i in s.indices) {
        if (s[i] != s[s.length - 1 - i]) {
            return false
        }
    }
    return true
}