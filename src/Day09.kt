import kotlin.math.abs

enum class Direction(val xDiff: Int, val yDiff: Int) {
    C(0, 0), R(1, 0), L(-1, 0),
    U(0, 1), UR(1, 1), UL(-1, 1),
    D(0, -1), DR(1, -1), DL(-1, -1)
}

fun main() {
    fun part1(input: List<String>): Int {
        val visited = mutableSetOf(Pair(0, 0))
        var headPosition = Pair(0, 0)
        var tailPosition = Pair(0, 0)

        input.map { it.split(" ") }.forEach { entry ->
            val direction = Direction.valueOf(entry[0])
            val steps = entry[1].toInt()
            println("======================================")
            println("$direction, $steps")

            headPosition = Pair(
                headPosition.first + (direction.xDiff * steps),
                headPosition.second + (direction.yDiff * steps)
            )

            println("Ended in: $headPosition, tail at $tailPosition")

            // shouldn't move if its in the immediate vicinity
            val shouldMove = Direction.values().all { diff ->
                Pair(
                    tailPosition.first + diff.xDiff,
                    tailPosition.second + diff.yDiff
                ) != headPosition
            }

            println("should move? $shouldMove")
            if (shouldMove) {
                // align depending on where did we move
                when (direction) {
                    Direction.R, Direction.L -> tailPosition = Pair(tailPosition.first, headPosition.second)
                    Direction.U, Direction.D -> tailPosition = Pair(headPosition.first, tailPosition.second)
                    else -> {}
                }
                println("after alignment $tailPosition")
                // tail and head on the same col
                if (headPosition.first == tailPosition.first) {
                    println("comp in ${abs(headPosition.first - tailPosition.first)}")
                    while (abs(headPosition.second - tailPosition.second) > 1) {
                        tailPosition = Pair(
                            tailPosition.first,
                            tailPosition.second + direction.yDiff
                        )
                        println("tail new pos $tailPosition")
                        visited.add(tailPosition)
                    }
                }
                // tail and head on the same row
                if (headPosition.second == tailPosition.second) {
                    println("comp in ${abs(headPosition.first - tailPosition.first)}")
                    while (abs(headPosition.first - tailPosition.first) > 1) {
                        tailPosition = Pair(
                            tailPosition.first + direction.xDiff,
                            tailPosition.second
                        )
                        println("tail new pos $tailPosition")
                        visited.add(tailPosition)
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
    /*
    check(part2(buildTreeGrid(testInput)) == 8)

    println(part2(buildTreeGrid(input)))

     */
}

