data class Monkey(
    val items: ArrayDeque<Int>,
    val operation: List<String>,
    val test: Int,
    val nextMonkeyRule: Map<Boolean, Int>,
    var inspectionCount: Int = 0,
)

fun main() {
    fun part1(input: List<String>): Int {
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
        repeat(20) {
            monkeys.forEach { monkey ->
                monkey.inspectionCount += monkey.items.size

                monkey.items.map { item ->
                    val const = if (monkey.operation.last() == "old") item else monkey.operation.last().toInt()
                    val nextItem =
                        with(monkey.operation[1]) {
                            when {
                                equals("+") -> item + const
                                equals("*") -> item * const
                                equals("-") -> item - const
                                else -> item
                            }
                        }
                    Integer.valueOf(nextItem / 3)
                }.forEach { item ->
                    val nextMonkeyIndex =
                        monkey.nextMonkeyRule[item % monkey.test == 0]!!
                    monkeys[nextMonkeyIndex].items.add(item)
                }

                monkey.items.clear()
            }
        }
        println(monkeys)
        return monkeys.sortedByDescending { it.inspectionCount }.subList(0, 2).fold(1) {acc: Int, monkey: Monkey -> monkey.inspectionCount * acc}
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 10605)

    val input = readInput("Day11")
    println(part1(input))
    /*
    check(displayCRT(testInput) == 13140)

    displayCRT(input)
    */
}

