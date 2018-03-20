package com.xuhj.kotlin.koans.convertions.comparison

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/5
 */
fun main(args: Array<String>) {

}

// ------------------------------------------------------------------------------------------------
/**
 * In range
 */
//operator fun MyDate.rangeTo(other: MyDate) = DateRange(this, other)
//
//class DateRange(override val start: MyDate,
//                override val endInclusive: MyDate) : ClosedRange<MyDate>
//
//fun checkInRange(date: MyDate, first: MyDate, last: MyDate): Boolean {
//    return date in first..last
//}
// ------------------------------------------------------------------------------------------------
/**
 * In range
 */
//class DateRange(val start: MyDate, val endInclusive: MyDate) {
//
//    operator fun contains(other: MyDate): Boolean = other in start..endInclusive
//}
//
//fun checkInRange(date: MyDate, first: MyDate, last: MyDate): Boolean {
//    return date in DateRange(first, last)
//}
// ------------------------------------------------------------------------------------------------
/**
 * Comparison
 */
data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {

    override fun compareTo(other: MyDate): Int {
        return when {
            year != other.year -> year - other.year
            month != other.month -> month - other.month
            else -> dayOfMonth - other.dayOfMonth
        }
    }
}
