package com.xuhj.kotlin.koans.convertions.destructuringdeclarations

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/6
 */
class MyDate(val year: Int, val month: Int, val dayOfMonth: Int){
    operator fun component1()=year
    operator fun component2()=month
    operator fun component3()=dayOfMonth
}

fun isLeapDay(date: MyDate): Boolean {

    val (year, month, dayOfMonth) = date

    // 29 February of a leap year
    return year % 4 == 0 && month == 2 && dayOfMonth == 29
}