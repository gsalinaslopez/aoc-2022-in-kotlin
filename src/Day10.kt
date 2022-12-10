fun main() {
    fun displayCRT(input: List<String>): Int {
        var x = 1
        var acc = 0
        var i = 1
        input.forEach { instruction ->
            if ((i - 1) % 40 == 0) println()
            if ((i - 1) % 40 in x - 1..x + 1) print("#") else print(".")

            if ((i - 20) % 40 == 0) acc += (x * i)
            if (instruction.contains("addx")) {
                i++

                if ((i - 1) % 40 == 0) println()
                if ((i - 1) % 40 in x - 1..x + 1) print("#") else print(".")

                if ((i - 20) % 40 == 0) acc += (x * i)

                x += instruction.split(" ")[1].toInt()
            }
            i++
        }
        return acc
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(displayCRT(testInput) == 13140)

    val input = readInput("Day07")
    displayCRT(input)
}

