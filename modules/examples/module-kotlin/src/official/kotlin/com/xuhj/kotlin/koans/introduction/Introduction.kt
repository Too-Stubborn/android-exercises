package com.xuhj.kotlin.koans.introduction

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
fun getList(): List<Int> {
    return arrayListOf(1, 5, 2).sortedDescending()
}

// ------------------------------------------------------------------------------------------------
fun Int.r(): RationalNumber = RationalNumber(this, 1)

fun Pair<Int, Int>.r(): RationalNumber = RationalNumber(first, second)

data class RationalNumber(val numerator: Int, val denominator: Int)

// ------------------------------------------------------------------------------------------------
fun sendMessageToClient(client: Client?, message: String?, mailer: Mailer) {
    val email = client?.personalInfo?.email
    if (email != null && message != null) {
        mailer.sendMessage(email, message)
    }
}

class Client(val personalInfo: PersonalInfo?)
class PersonalInfo(val email: String?)
interface Mailer {
    fun sendMessage(email: String, message: String)
}
// ------------------------------------------------------------------------------------------------

class Person(val name: String, val age: Int) {
    override fun toString(): String {
        return "[Person(name='$name', age=$age)]"
    }
}
// ------------------------------------------------------------------------------------------------


val month = "(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)"
fun getPattern(): String = """\d{2} ${month} \d{4}"""

// ------------------------------------------------------------------------------------------------
fun containsEven(collection: Collection<Int>): Boolean = collection.any { it.mod(2) == 0 }

// ------------------------------------------------------------------------------------------------
fun toJSON(collection: Collection<Int?>): String {
    var sb = StringBuilder()
    sb.append("[")
    val itor = collection.iterator()
    while (itor.hasNext()) {
        val elem = itor.next()
        sb.append(elem)
        if (itor.hasNext()) {
            sb.append(",")
        }
    }
    sb.append("]")
    return sb.toString()
}