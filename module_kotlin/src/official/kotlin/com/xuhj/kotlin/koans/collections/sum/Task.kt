package com.xuhj.kotlin.koans.collections.sum

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/6
 */
fun main(args: Array<String>) {
    val price = customers[lucas]!!.getTotalOrderPrice()
    println(price)
}

// Return the sum of prices of all products that a customer has ordered.
// Note: the customer may order the same product for several times.
fun Customer.getTotalOrderPrice(): Double
        = orders.flatMap { it.products }.distinctBy { it.name }.sumByDouble { it.price }