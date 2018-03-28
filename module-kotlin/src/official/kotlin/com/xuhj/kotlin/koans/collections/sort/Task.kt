package com.xuhj.kotlin.koans.collections.sort

import java.util.*

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/6
 */
fun main(args: Array<String>) {
    val rlist = listOf(3, 1, 4, 2, 4)
    Collections.sort(rlist,Collections.reverseOrder())
    rlist.forEach { print(it) }
}

// Return a list of customers, sorted by the ascending number of orders they made
fun Shop.getCustomersSortedByNumberOfOrders(): List<Customer>
        = customers.sortedBy { it.orders.size }