package com.xuhj.kotlin.koans.collections.introduction

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/6
 */
fun main(args: Array<String>) {
    println(customers.values.toSet() == shop.getSetOfCustomers())
}

fun Shop.getSetOfCustomers(): Set<Customer> = customers.toSet()