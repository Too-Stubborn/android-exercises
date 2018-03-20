package com.xuhj.kotlin.koans.generics

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/7
 */
import java.util.*
import kotlin.collections.ArrayList

fun main() {
}

fun <T, C : MutableCollection<T>> Collection<T>.partitionTo(
        first: C,
        second: C,
        expr: (T) -> Boolean): Pair<C, C> {
    for (element in this) {
        if (expr(element)) {
            first.add(element)
        } else {
            second.add(element)
        }
    }
    return Pair(first, second)
}

fun partitionWordsAndLines() {
    val (words, lines) = listOf("a", "a b", "c", "d e").
            partitionTo(ArrayList<String>(), ArrayList()) { s -> !s.contains(" ") }
    words == listOf("a", "c")
    lines == listOf("a b", "d e")
}

fun partitionLettersAndOtherSymbols() {
    val (letters, other) = setOf('a', '%', 'r', '}').
            partitionTo(HashSet<Char>(), HashSet()) { c -> c in 'a'..'z' || c in 'A'..'Z' }
    letters == setOf('a', 'r')
    other == setOf('%', '}')
}