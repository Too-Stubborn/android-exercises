package com.xuhj.kotlin.koans.builders.stringandmap

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/7
 */

fun <K, V> buildMap(build: HashMap<K, V>.() -> Unit): Map<K, V> {
    var map = HashMap<K, V>()
    map.build()
    return map
}

fun usage(): Map<Int, String> {
    return buildMap {
        put(0, "0")
        for (i in 1..10) {
            put(i, "$i")
        }
    }
}