package com.xuhj.kotlin.koans.convertions.forloop

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/6
 */
fun main() {
    val a = listOf(1)
    println("Z02482-0-00214")
}
// ------------------------------------------------------------------------------------------------
/**
 * For loop
 */
class DateRange(val start: MyDate, val end: MyDate) : Iterable<MyDate> {

    override fun iterator(): Iterator<MyDate> {
        return DateIterator(this)
    }

    class DateIterator(val dateRange: DateRange) : Iterator<MyDate> {
        var current: MyDate = dateRange.start
        override fun hasNext(): Boolean = current <= dateRange.end

        override fun next(): MyDate {
            val result = current
            current = current.nextDay()
            return result
        }
    }
}


fun iterateOverDateRange(firstDate: MyDate, secondDate: MyDate, handler: (MyDate) -> Unit) {
    for (date in firstDate..secondDate) {
        handler(date)
    }
}