import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

enum class Direction(val xDiff: Int, val yDiff: Int) {
    C(0, 0), R(1, 0), L(-1, 0),
    U(0, 1), UR(1, 1), UL(-1, 1),
    D(0, -1), DR(1, -1), DL(-1, -1)
}

fun main() {
    fun distance(x1: Int, x2: Int, y1: Int, y2: Int) =
        sqrt((x2 - x1).toDouble().pow(2) + (y2 - y1).toDouble().pow(2))

    fun moveHead(head: IntArray, direction: Direction) {
        println("======================================")
        println("$direction")

        head[0] = head[0] + direction.xDiff
        head[1] = head[1] + direction.yDiff
    }

    fun tailFollow(tail: IntArray, head: IntArray): Set<Pair<Int, Int>> {
        val visited = mutableSetOf<Pair<Int, Int>>()

        // shouldn't move if its in the immediate vicinity
        val shouldMove = Direction.values().all { diff ->
            tail[0] + diff.xDiff != head[0] || tail[1] + diff.yDiff != head[1]
        }
        println("should move? $shouldMove tail -> ${tail.contentToString()} head -> ${head.contentToString()}")

        if (shouldMove) {
            // tail and head on the same col
            if (head[0] == tail[0]) {
                if (head[1] > tail[1]) {
                    tail[1]++
                } else {
                    tail[1]--
                }
                /*
                while (abs(head[1] - tail[1]) > 1) {
                    tail[1] = tail[1] + direction.yDiff
                    println("tail new pos 1 ${tail.contentToString()}")
                }
                 */
            }
            // tail and head on the same row
            else if (head[1] == tail[1]) {
                if (head[0] > tail[0]) {
                    tail[0]++
                } else {
                    tail[0]--
                }
                /*
                while (abs(head[0] - tail[0]) > 1) {
                    tail[0] = tail[0] + direction.xDiff
                    println("tail new pos 2 ${tail.contentToString()}")
                }
                 */
            }
            // forced to move diagonally
            else {
                println("last else")
                val diagonalPositions = Direction.values().filter {
                    it != Direction.C && it != Direction.R && it != Direction.L && it != Direction.U && it != Direction.D
                }.map { diff ->
                    intArrayOf(tail[0] + diff.xDiff, tail[1] + diff.yDiff)
                }.minBy {
                    //sqrt((head[0] - it[0]).toDouble().pow(2) + (head[1] - it[1]).toDouble().pow(2))
                    distance(it[0], head[0], it[1], head[1])
                }
                tail[0] = diagonalPositions[0]
                tail[1] = diagonalPositions[1]
            }
            println("tail new pos ${tail.contentToString()}")
            visited.add(Pair(tail[0], tail[1]))
        }
        println("out of step")
        return visited
    }

    fun part1(input: List<String>, numOfTails: Int): Int {
        val visited = mutableSetOf(Pair(0, 0))
        val nodes = Array(numOfTails + 1) { intArrayOf(0, 0) }

        input.map { it.split(" ") }.forEach { entry ->
            val direction = Direction.valueOf(entry[0])
            val steps = entry[1].toInt()

            repeat(steps) {
                moveHead(nodes.last(), direction)
                for (i in nodes.size - 2 downTo 0) {
                    val moves = tailFollow(nodes[i], nodes[i + 1])
                    if (i == 0) {
                        visited.addAll(moves)
                    }
                }
            }
            //visited.addAll(tailFollow(tail, head, direction))
        }
        println(visited)
        return visited.size
    }
    // test if implementation meets criteria from the description, like:
    var testInput = readInput("Day07_test")
    check(part1(testInput, 1) == 13)
    testInput = readInput("Day07_test2")
    check(part1(testInput, 9) == 36)

    val input = readInput("Day07")
    //println(part1(input, 1))
    check(part1(input, 1) == 6256)
    check(part1(input, 9) == 2665)
    /*
    check(part2(buildTreeGrid(testInput)) == 8)

    println(part2(buildTreeGrid(input)))

     */
}


