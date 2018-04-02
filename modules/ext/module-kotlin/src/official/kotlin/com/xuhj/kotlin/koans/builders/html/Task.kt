package com.xuhj.kotlin.koans.builders.html

import junit.framework.Assert

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/7
 */
fun main(args: Array<String>) {
    test1()
}

fun renderProductTable(): String {
    return html {
        table {
            tr(color = getTitleColor()) {
                td {
                    text("Product")
                }
                td {
                    text("Price")
                }
                td {
                    text("Popularity")
                }
            }
            val products = getProducts()
            for ((index, value) in products.withIndex()) {
                tr {
                    td(color = getCellColor(index, 0)) {
                        text(value.description)
                    }
                    td(color = getCellColor(index, 1)) {
                        text(value.price)
                    }
                    td(color = getCellColor(index, 2)) {
                        text(value.popularity)
                    }
                }
            }
        }
    }.toString()
}

fun getTitleColor() = "#b9c9fe"
fun getCellColor(row: Int, column: Int) = if ((row + column) % 2 == 0) "#dce4ff" else "#eff2ff"

// ------------------------------------------------------------------------------------------------
/**
 * how it works
 */
enum class Answer { a, b, c }

val answers = mapOf<Int, Answer?>(
        2 to Answer.b, 4 to Answer.c, 3 to Answer.b, 1 to Answer.c
)

fun test1() {
    if (answers.values.toSet() == setOf(null)) {
        Assert.fail("Please specify your answers!")
    }
    val correctAnswers = mapOf(22 - 20 to Answer.b, 1 + 3 to Answer.c,
            11 - 8 to Answer.b, 79 - 78 to Answer.c)
    if (correctAnswers != answers) {
        val incorrect = (1..4).filter { answers[it] != correctAnswers[it] }
        Assert.fail("Your answers are incorrect! $incorrect")
    }
}