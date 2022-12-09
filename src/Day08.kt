fun main() {
    fun buildTreeGrid(input: List<String>) =
        Array(input.size) { i -> Array(input[i].length) { j -> input[i][j].digitToInt() } }

    fun part1(treeGrid: Array<Array<Int>>): Int {
        val markerGrid = Array(treeGrid.size) { i ->
            Array(treeGrid[i].size) { j ->
                i == 0 || i == treeGrid.size - 1 || j == 0 || j == treeGrid[i].size - 1
            }
        }

        // left to right swipe
        for (i in treeGrid.indices) {
            var maxSoFar = -1
            treeGrid[i].forEachIndexed { j, treeHeight ->
                if (treeHeight > maxSoFar) {
                    markerGrid[i][j] = true
                }
                maxSoFar = maxOf(maxSoFar, treeHeight)
            }
        }

        // right to left swipe
        for (i in treeGrid.indices) {
            var maxSoFar = -1
            for (j in treeGrid.size - 1 downTo 0) {
                if (treeGrid[i][j] > maxSoFar) {
                    markerGrid[i][j] = true
                }
                maxSoFar = maxOf(maxSoFar, treeGrid[i][j])
            }
        }

        // up to down swipe
        for (j in treeGrid[0].indices) {
            var maxSoFar = -1
            for (i in treeGrid.indices) {
                if (treeGrid[i][j] > maxSoFar) {
                    markerGrid[i][j] = true
                }
                maxSoFar = maxOf(maxSoFar, treeGrid[i][j])
            }
        }

        // down to up swipe
        for (j in treeGrid[0].indices) {
            var maxSoFar = -1
            for (i in treeGrid.size - 1 downTo 0) {
                if (treeGrid[i][j] > maxSoFar) {
                    markerGrid[i][j] = true
                }
                maxSoFar = maxOf(maxSoFar, treeGrid[i][j])
            }
        }
        return markerGrid.sumOf { row -> row.count { it } }
    }

    fun part2(treeGrid: Array<Array<Int>>): Int {
        var maxSoFar = -1

        for (i in 1 until treeGrid.size - 1) {
            for (j in 1 until treeGrid[i].size - 1) {
                // right
                var rightCount = 1
                for (k in j + 1 until treeGrid[i].size - 1) {
                    if (treeGrid[i][k] >= treeGrid[i][j]) {
                        break
                    }
                    rightCount++
                }
                // left
                var leftCount = 1
                if (j > 0) {
                    for (k in j - 1 downTo 1) {
                        if (treeGrid[i][k] >= treeGrid[i][j]) {
                            break
                        }
                        leftCount++
                    }
                }
                // down
                var downCount = 1
                for (k in i + 1 until treeGrid.size - 1) {
                    if (treeGrid[k][j] >= treeGrid[i][j]) {
                        break
                    }
                    downCount++
                }
                // up
                var upCount = 1
                if (i > 0) {
                    for (k in i - 1 downTo 1) {
                        if (treeGrid[k][j] >= treeGrid[i][j]) {
                            break
                        }
                        upCount++
                    }
                }
                maxSoFar = maxOf(maxSoFar, (leftCount * rightCount * upCount * downCount))
            }
        }
        return maxSoFar
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(buildTreeGrid(testInput)) == 21)
    check(part2(buildTreeGrid(testInput)) == 8)

    val input = readInput("Day08")
    println(part1(buildTreeGrid(input)))
    println(part2(buildTreeGrid(input)))
}

