import java.util.*
import kotlin.math.abs

enum class Direction(val xDiff: Int, val yDiff: Int) {
    C(0, 0), R(1, 0), L(-1, 0),
    U(0, 1), UR(1, 1), UL(-1, 1),
    D(0, -1), DR(1, -1), DL(-1, -1)
}

fun main() {
    fun moveHead(head: IntArray, direction: Direction, steps: Int) {

        println("======================================")
        println("$direction, $steps")

        head[0] = head[0] + (direction.xDiff * steps)
        head[1] = head[1] + (direction.yDiff * steps)
    }

    fun part1(input: List<String>): Int {
        val visited = mutableSetOf(Pair(0, 0))
        val head = intArrayOf(0, 0)
        val tail = intArrayOf(0, 0)

        input.map { it.split(" ") }.forEach { entry ->
            val direction = Direction.valueOf(entry[0])
            val steps = entry[1].toInt()
            moveHead(head, direction, steps)

            println("Ended in: ${head.contentToString()}, tail at ${tail.contentToString()}")

            // shouldn't move if its in the immediate vicinity
            val shouldMove = Direction.values().all { diff ->
                //println("${tail[0] + diff.xDiff} != ${head[0]} && ${tail[1] + diff.yDiff} != ${head[1]}")
                tail[0] + diff.xDiff != head[0] || tail[1] + diff.yDiff != head[1]
            }

            println("should move? $shouldMove")
            if (shouldMove) {
                // align depending on where did we move
                when (direction) {
                    Direction.R, Direction.L -> tail[1] = head[1]
                    Direction.U, Direction.D -> tail[0] = head[0]
                    else -> {}
                }
                println("after alignment ${tail.contentToString()}")
                // tail and head on the same col
                if (head[0] == tail[0]) {
                    while (abs(head[1] - tail[1]) > 1) {
                        tail[1] = tail[1] + direction.yDiff
                        println("tail new pos $tail")
                        visited.add(Pair(tail[0], tail[1]))
                    }
                }
                // tail and head on the same row
                if (head[1] == tail[1]) {
                    while (abs(head[0] - tail[0]) > 1) {
                        tail[0] = tail[0] + direction.xDiff
                        println("tail new pos $tail")
                        visited.add(Pair(tail[0], tail[1]))
                    }
                }
            }
        }

        println(visited)
        return visited.size
    }
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    println(part1(testInput))
    check(part1(testInput) == 13)

    val input = readInput("Day07")
    println(part1(input))
    check(part1(input) == 6256)
    /*
    check(part2(buildTreeGrid(testInput)) == 8)

    println(part2(buildTreeGrid(input)))

     */
}


