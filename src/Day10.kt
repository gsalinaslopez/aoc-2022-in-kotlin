fun main() {
    fun part1(input: List<String>): Int {
        var x = 1
        var acc = 0
        var i = 1
        input.forEach { instruction ->
            println("beginning of $i cycle: ${x}")

            if ((i - 20) % 40 == 0) {
                println(x * i)
                acc += (x * i)
            }

            println("end of $i cycle: ${x}")

            if (instruction.contains("addx")) {
                i++
                println("beginning of $i cycle: ${x}")

                if ((i - 20) % 40 == 0) {
                    println(x * i)
                    acc += (x * i)
                }

                x += instruction.split(" ")[1].toInt()

                println("end of $i cycle: ${x}")
            } else {
                0
            }
            i++
        }
        return acc
    }

    // test if implementation meets criteria from the description, like:
    var testInput = readInput("Day07_test")
    //part1(testInput)
    testInput = readInput("Day07_test2")
    println(part1(testInput))
    check(part1(testInput) == 13140)

    val input = readInput("Day07")
    println(part1(input))
    /*
    check(part1(buildTreeGrid(testInput)) == 21)
    check(part2(buildTreeGrid(testInput)) == 8)

    println(part2(buildTreeGrid(input)))
    */
}

