import TimeInterval.*

fun main(args: Array<String>) {

    println(MyDate(2017, 6, 30) == task1(MyDate(2016, 6, 23)))
    println(MyDate(2017, 6, 30) == task1(MyDate(2016, 6, 24)))

    println(MyDate(2017, 6, 30) == task2(MyDate(2015, 6, 4)))
    println(MyDate(2017, 6, 30) == task2(MyDate(2015, 6, 5)))
}

/**
 * Date
 */
data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int)

enum class TimeInterval { DAY, WEEK, YEAR }
/**
 * TimeIntervalTimes
 */
class TimeIntervalTimes(val timeInterval: TimeInterval, val number: Int)

/**
 * operator
 */
operator fun MyDate.plus(timeInterval: TimeInterval): MyDate
        = addTimeIntervals(timeInterval, 1)

operator fun MyDate.plus(timeIntervalTimes: TimeIntervalTimes): MyDate
        = addTimeIntervals(timeIntervalTimes.timeInterval, timeIntervalTimes.number)

operator fun TimeInterval.times(times: Int) = TimeIntervalTimes(this, times)

/**
 * Task
 */
fun task1(today: MyDate): MyDate {
    return today + YEAR + WEEK
}

fun task2(today: MyDate): MyDate {
    return today + YEAR * 2 + WEEK * 3 + DAY * 5
}