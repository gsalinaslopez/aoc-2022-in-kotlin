fun main() {
    fun getMarkerIndex(input: String, window: Int): Int =
        input.windowed(window).indexOfFirst { it.toSet().size == window } + window

    fun part1(input: String): Int = getMarkerIndex(input, 4)

    fun part2(input: String): Int = getMarkerIndex(input, 14)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput[0]) == 7)
    check(part2(testInput[0]) == 19)

    val input = readInput("Day06")
    println(part1(input[0]))
    println(part2(input[0]))
}
