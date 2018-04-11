package com.xuhj.kotlin.koans.collections.fold

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/6
 */
fun main(args: Array<String>) {
    val result = listOf(1, 2, 3, 4).fold(1, {
        partProduct, element ->
        element + partProduct
    })
    println(result)
}

// Return the set of products that were ordered by every customer
fun Shop.getSetOfProductsOrderedByEveryCustomer(): Set<Product> {
    val allProducts = customers.flatMap { it.orders.flatMap { it.products } }.toSet()
    return customers
            .fold(allProducts) {
                result, customer ->
                result.intersect(customer.orders.flatMap { it.products })
            }
}