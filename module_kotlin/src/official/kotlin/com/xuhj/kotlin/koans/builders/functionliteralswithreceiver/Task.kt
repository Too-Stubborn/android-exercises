package com.xuhj.kotlin.koans.builders.functionliteralswithreceiver

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/7
 */
fun task(): List<Boolean> {
    val isEven: Int.() -> Boolean = { this % 2 == 0 }
    val isOdd: Int.() -> Boolean = { this % 2 != 0 }

    return listOf(42.isOdd(), 239.isOdd(), 294823098.isEven())
}