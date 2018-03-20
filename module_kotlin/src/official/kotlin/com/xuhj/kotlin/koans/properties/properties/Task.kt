package com.xuhj.kotlin.koans.properties.properties

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/6
 */
fun main(args: Array<String>) {
    var p = PropertyExample()
    p.propertyWithCounter = 11
    println(p.propertyWithCounter ?: "null")
}

class PropertyExample() {
    var counter = 0
    var propertyWithCounter: Int? = null
        get() {
            return field
        }
        set(value) {
            counter++
            field = value
        }
}