fun main() {
    fun getRanges(input: List<String>): List<Pair<IntRange, IntRange>> = input
        .map { entry ->
            val sections = entry.split(",")
            val firstSection = sections.first().split("-").map { it.toInt() }
            val secondSection = sections.last().split("-").map { it.toInt() }
            val firstRange = firstSection.first()..firstSection.last()
            val secondRange = secondSection.first()..secondSection.last()

            Pair(firstRange, secondRange)
        }

    fun part1(input: List<String>): Int = getRanges(input)
        .sumOf { entry ->
            val (firstRange, secondRange) = entry
            if (firstRange.first() in secondRange && firstRange.last() in secondRange ||
                secondRange.first() in firstRange && secondRange.last() in firstRange
            ) {
                Integer.valueOf(1)
            } else {
                Integer.valueOf(0)
            }
        }

    fun part2(input: List<String>): Int = getRanges(input)
        .sumOf { entry ->
            val (firstRange, secondRange) = entry
            if (firstRange.first() in secondRange || firstRange.last() in secondRange ||
                secondRange.first() in firstRange || secondRange.last() in firstRange
            ) {
                Integer.valueOf(1)
            } else {
                Integer.valueOf(0)
            }
        }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
