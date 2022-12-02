enum class Moves(val points: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3)
}

enum class Results(val points: Int) {
    WIN(6),
    DRAW(3),
    LOSE(0)
}

val modesMap = mapOf(
    "A" to Moves.ROCK, "X" to Moves.ROCK,
    "B" to Moves.PAPER, "Y" to Moves.PAPER,
    "C" to Moves.SCISSORS, "Z" to Moves.SCISSORS,
)

val resultsMap = mapOf(
    "X" to Results.LOSE,
    "Y" to Results.DRAW,
    "Z" to Results.WIN
)

fun main() {
    fun part1(input: List<String>): Int {
        var score = 0
        input.forEach {
            val move = it.split(" ")
            val opMove = modesMap[move.first()]!!
            val myMove = modesMap[move.last()]!!

            score += myMove.points + when (myMove) {
                Moves.ROCK -> when (opMove) {
                    Moves.ROCK -> Results.DRAW.points
                    Moves.PAPER -> Results.LOSE.points
                    Moves.SCISSORS -> Results.WIN.points
                }
                Moves.PAPER -> when (opMove) {
                    Moves.ROCK -> Results.WIN.points
                    Moves.PAPER -> Results.DRAW.points
                    Moves.SCISSORS -> Results.LOSE.points
                }
                Moves.SCISSORS -> when (opMove) {
                    Moves.ROCK -> Results.LOSE.points
                    Moves.PAPER -> Results.WIN.points
                    Moves.SCISSORS -> Results.DRAW.points
                }
            }
        }

        return score
    }

    fun part2(input: List<String>): Int {
        var score = 0
        input.forEach {
            val move = it.split(" ")
            val opMove = modesMap[move.first()]!!
            val result = resultsMap[move.last()]!!
            score += result.points + when (opMove) {
                Moves.ROCK -> when (result) {
                    Results.WIN -> Moves.PAPER.points
                    Results.DRAW -> Moves.ROCK.points
                    Results.LOSE -> Moves.SCISSORS.points
                }
                Moves.PAPER -> when (result) {
                    Results.WIN -> Moves.SCISSORS.points
                    Results.DRAW -> Moves.PAPER.points
                    Results.LOSE -> Moves.ROCK.points
                }
                Moves.SCISSORS -> when (result) {
                    Results.WIN -> Moves.ROCK.points
                    Results.DRAW -> Moves.SCISSORS.points
                    Results.LOSE -> Moves.PAPER.points
                }
            }
        }

        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
