package com.xuhj.kotlin.examples.longerexamples

import java.util.*

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/1
 */
fun main(args: Array<String>) {

    walk("""
    OOOOOOOOOOO
    O $       O
    OOOOOOO OOO
    O         O
    OOOOO OOOOO
    O         O
    O OOOOOOOOO
    O        OO
    OOOOOO   IO
  """)
    walk("""
    OOOOOOOOOOOOOOOOO
    O               O
    O$  O           O
    OOOOO           O
    O          O    O
    OOOOOOOOOOOOOOO O
    O             O O
    O  OOOOO  OO    O
    O  O          O O
    O  OO  OOOOOOOO O
    O  O            O
    O  OOOOOOOOOOOOOO
    O   O   O   O I O
    O     O   O     O
    OOOOOOOOOOOOOOOOO
  """.trimMargin())
}

data class Point(val i: Int, val j: Int)

class Maze(val width: Int,
           val height: Int,
           val walls: Array<BooleanArray>,
           val start: Point,
           val end: Point)

fun makeMaze(s: String): Maze {
    val lines = s.split('\n')
    val longestLine = lines.toList().maxBy { it.length } ?: ""
    val data = Array(lines.size) { BooleanArray(longestLine.length) }

    var start: Point? = null
    var end: Point? = null

    for (i in lines.indices) {
        for (j in lines[i].indices) {
            val c = lines[i][j]
            when (c) {
                'O' -> data[i][j] = true
                'I' -> start = Point(i, j)
                '$' -> end = Point(i, j)
            }
        }
    }
    return Maze(longestLine.length, lines.size, data,
            start ?: throw KotlinNullPointerException("start point is null"),
            end ?: throw KotlinNullPointerException("end point is null"))
}

fun findPath(maze: Maze): List<Point>? {
    val previous = hashMapOf<Point, Point>()

    val queue = LinkedList<Point>()
    val visited = hashSetOf<Point>()

    queue.offer(maze.start)
    visited.add(maze.start)
    while (!queue.isEmpty()) {
        val cell = queue.poll()
        if (cell == maze.end) break

        for (newCell in maze.neighbors(cell.i, cell.j)) {
            if (newCell in visited) continue
            previous.put(newCell, cell)
            queue.offer(newCell)
            visited.add(cell)
        }
    }
    if (previous[maze.end] == null) {
        return null
    }
    val path = arrayListOf<Point>()
    var current = previous[maze.end]!!
    while (current != maze.start) {
        path.add(0, current)
        current = previous[current]!!
    }
    return path
}

fun Maze.neighbors(i: Int, j: Int): List<Point> {
    val result = arrayListOf<Point>()
//    addIfFree(i - 1, j, result)  // up
//    addIfFree(i, j - 1, result)  // left
//    addIfFree(i + 1, j, result)  // down
//    addIfFree(i, j + 1, result)  // right
    addIfFree(i - 1, j, result)  // up
    addIfFree(i, j - 1, result)  // left
    addIfFree(i + 1, j, result)  // down
    addIfFree(i, j + 1, result)  // right
    return result
}

fun Maze.addIfFree(i: Int, j: Int, result: MutableList<Point>) {
    if (i !in 0..height - 1) return
    if (j !in 0..width - 1) return
    if (walls[i][j]) return
    result.add(Point(i, j))
}

fun walk(s: String) {
    val maze = makeMaze(s)
    println("Maze:")
    val path = findPath(maze)
    for (i in 0..maze.height - 1) {
        for (j in 0..maze.width - 1) {
            val cell = Point(i, j)
            print(
                    if (maze.walls[i][j]) "0"
                    else if (cell == maze.start) "I"
                    else if (cell == maze.end) "$"
                    else if (path != null && path.contains(cell)) "~"
                    else " "
            )
        }
        println("")
    }
    println("Result: ${if (path == null) "No path" else "Path found"}")
    println("")
}

