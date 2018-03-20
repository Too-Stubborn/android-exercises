package com.xuhj.kotlin.koans.collections.groupby

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/6
 */
fun main(args: Array<String>) {
    val result = listOf("a", "b", "ba", "ccc", "ad").groupBy { it.length }
    val flag = result == mapOf(1 to listOf("a", "b"), 2 to listOf("ba", "ad"),
            3 to listOf("ccc"))
    println(flag)
}

// Return a map of the customers living in each city
fun Shop.groupCustomersByCity(): Map<City, List<Customer>>
        = customers.groupBy { it.city }
