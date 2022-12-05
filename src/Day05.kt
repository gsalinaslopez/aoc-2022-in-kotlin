fun main() {
    fun getCrates(input: List<String>): Array<ArrayDeque<Char>> {
        val reversedCratesString = input.filter { it.contains('[') }.reversed()
        val numOfCrates = reversedCratesString.first().count { it == '[' }
        val crates = Array<ArrayDeque<Char>>(numOfCrates) { ArrayDeque() }

        reversedCratesString.forEach {
            var cratesString = it
            var crateIndex = 0
            while (cratesString.length >= 3) {
                val crate = cratesString.substring(0, 3)
                if (crate.contains('[') && crate.contains(']')) {
                    crates[crateIndex].add(crate[1])
                }
                if (cratesString.length >= 4) {
                    cratesString = cratesString.substring(4)
                    crateIndex += 1
                } else {
                    break
                }
            }
        }
        return crates
    }

    data class Instruction(val numOfCrates: Int, val source: Int, val dest: Int)

    fun getInstructions(input: String): Instruction {
        val instructions = input.split(" ")
        return Instruction(
            numOfCrates = instructions[1].toInt(),
            source = instructions[3].toInt() - 1,
            dest = instructions[5].toInt() - 1
        )
    }

    fun getTopCrates(crates: Array<ArrayDeque<Char>>): String =
        Array(crates.size) { i -> crates[i].last() }.joinToString(separator = "") { it.toString() }

    fun part1(input: List<String>): String {
        val crates = getCrates(input)
        input.filter { it.startsWith("move") }.forEach { instruction ->
            val (numOfCrates, source, dest) = getInstructions(instruction)

            for (i in 0 until numOfCrates) {
                crates[dest].add(crates[source].last())
                crates[source].removeLast()
            }
        }
        return getTopCrates(crates)
    }

    fun part2(input: List<String>): String {
        val crates = getCrates(input)
        input.filter { it.startsWith("move") }.forEach { instruction ->
            val (numOfCrates, source, dest) = getInstructions(instruction)
            val tempCrate = ArrayDeque<Char>()

            for (i in 0 until numOfCrates) {
                tempCrate.add(crates[source].last())
                crates[source].removeLast()
            }
            for (i in 0 until numOfCrates) {
                crates[dest].add(tempCrate.last())
                tempCrate.removeLast()
            }
        }
        return getTopCrates(crates)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
