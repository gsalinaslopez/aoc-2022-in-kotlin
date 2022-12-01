fun main() {
    fun part1(input: List<String>): Int {
        var currentElf = 0
        var maxElf = -1

        input.forEach {
            if (it.isEmpty()) {
                maxElf = maxOf(maxElf, currentElf)
                currentElf = 0
            } else {
                currentElf += it.toInt()
            }
        }

        return maxElf
    }

    fun part2(input: List<String>): Int {
        val elfRations = mutableListOf<Int>()
        var currentElf = 0

        input.forEach {
            if (it.isEmpty()) {
                elfRations.add(currentElf)
                currentElf = 0
            } else {
                currentElf += it.toInt()
            }
        }

        return elfRations.sorted().reversed().subList(0, 3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
