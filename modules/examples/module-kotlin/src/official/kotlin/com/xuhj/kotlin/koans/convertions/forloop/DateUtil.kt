package com.xuhj.kotlin.koans.convertions.forloop

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/6
 */
import com.xuhj.kotlin.koans.convertions.forloop.MyDate
import java.util.*

fun MyDate.nextDay() = addTimeIntervals(TimeInterval.DAY, 1)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

fun MyDate.addTimeIntervals(timeInterval: TimeInterval, number: Int): MyDate {
    val c = Calendar.getInstance()
    c.set(year, month, dayOfMonth)
    when (timeInterval) {
        TimeInterval.DAY -> c.add(Calendar.DAY_OF_MONTH, number)
        TimeInterval.WEEK -> c.add(Calendar.WEEK_OF_MONTH, number)
        TimeInterval.YEAR -> c.add(Calendar.YEAR, number)
    }
    return MyDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE))
}