data class Monkey(
    val items: ArrayDeque<Int>,
    val operation: List<String>,
    val test: Int,
    val nextMonkeyRule: Map<Boolean, Int>,
)

fun main() {
    fun part1(input: List<String>) {
        val monkeys = mutableListOf<Monkey>()

        // Init monkeys
        input.chunked(7).forEach { instruction ->
            // Monkey index
            //val monkeyIndex = instruction[0].substringAfter(" ").substringBefore(":").toInt()

            // Starting items
            val items = ArrayDeque<Int>()
            instruction[1].substringAfter(": ").replace(",", "").trim().split(" ").forEach {
                items.add(it.toInt())
            }

            // Operation
            val operation = instruction[2].substringAfter("= ").trim().split(" ")

            // Divisibility Test
            val test = instruction[3].substringAfter("by ").trim().toInt()

            // Next monkey rule
            val nextMonkeyRule = mapOf(
                true to instruction[4].split(" ").last().toInt(),
                false to instruction[5].split(" ").last().toInt()
            )

            val monkey = Monkey(
                items = items,
                operation = operation,
                test = test,
                nextMonkeyRule = nextMonkeyRule
            )
            monkeys.add(monkey)
        }
        println(monkeys)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    println(part1(testInput))
    /*
    check(displayCRT(testInput) == 13140)

    val input = readInput("Day10")
    displayCRT(input)
    */
}

