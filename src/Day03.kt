fun main() {
    fun getRucksackIntersection(rucksack: List<String>): Int = rucksack
        .map { it.toSet() }
        .reduce { acc, chars -> acc.intersect(chars) }
        .first().code.let {
            if (it <= 90) {
                it - 38
            } else {
                it - 96
            }
        }

    fun part1(input: List<String>): Int = input.sumOf { rucksack ->
        getRucksackIntersection(rucksack.chunked(rucksack.length / 2))
    }

    fun part2(input: List<String>): Int = input.chunked(3).sumOf { rucksack ->
        getRucksackIntersection(rucksack)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
