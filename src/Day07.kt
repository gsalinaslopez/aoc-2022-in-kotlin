const val TOTAL_DISK_SPACE = 70000000
const val UPDATE_SIZE = 30000000

enum class FileType { FILE, DIR }

data class FileSystemEntry(val size: Long, val name: String, val fileType: FileType)

fun main() {
    fun buildFileSystemTree(input: List<String>): Map<FileSystemEntry, List<FileSystemEntry>> {
        val dirNavigationStack = ArrayDeque<String>()
        val fileSystemTree = mutableMapOf<FileSystemEntry, MutableList<FileSystemEntry>>()

        var i = 0
        while (i < input.size) {
            val instructionLine = input[i]
            when {
                instructionLine.contains("$ cd") -> {
                    with(instructionLine.split(" ")[2]) {
                        when {
                            equals("..") -> dirNavigationStack.removeLast()
                            equals("/") -> dirNavigationStack.clear()
                                .also { dirNavigationStack.add(this) }
                            else -> dirNavigationStack.add(this)
                        }
                    }
                }
                instructionLine.contains("$ ls") -> {
                    val currentPath = "/" + dirNavigationStack.subList(1, dirNavigationStack.size)
                        .joinToString(separator = "/")
                    val currentDir = FileSystemEntry(-1, currentPath, FileType.DIR)

                    fileSystemTree[currentDir] = mutableListOf()
                    input.subList(i + 1, input.size).takeWhile { !it.contains("$") }.forEach {
                        val entry = it.split(" ")
                        val filePath = if (currentPath.length == 1) {
                            "/${entry[1]}"
                        } else {
                            "$currentPath/${entry[1]}"
                        }

                        val fileSystemEntry = if (entry[0].contains("dir")) {
                            FileSystemEntry(-1, filePath, FileType.DIR)
                        } else {
                            FileSystemEntry(entry[0].toLong(), filePath, FileType.FILE)
                        }
                        fileSystemTree[currentDir]?.add(fileSystemEntry)

                        i++
                    }
                }
            }
            i++
        }

        return fileSystemTree
    }

    fun buildDirList(
        fileSystemTree: Map<FileSystemEntry, List<FileSystemEntry>>
    ): List<FileSystemEntry> {
        val dirs = mutableListOf<FileSystemEntry>()
        fun rec(fileSystemEntry: FileSystemEntry): Long {
            val totalSum = fileSystemTree[fileSystemEntry]?.sumOf {
                when (it.fileType) {
                    FileType.FILE -> it.size
                    FileType.DIR -> rec(it)
                }
            } ?: 0

            dirs.add(FileSystemEntry(totalSum, fileSystemEntry.name, FileType.DIR))
            return totalSum
        }
        rec(fileSystemEntry = FileSystemEntry(-1, "/", FileType.DIR))

        return dirs
    }

    fun part1(input: List<String>): Long {
        val fileSystemTree = buildFileSystemTree(input)
        val dirs = buildDirList(fileSystemTree)

        return dirs.filter { it.size <= 100000L }.sumOf { it.size }
    }


    fun part2(input: List<String>): Long {
        val fileSystemTree = buildFileSystemTree(input)
        val dirs = buildDirList(fileSystemTree)

        val remainingSize = TOTAL_DISK_SPACE - dirs.first { it.name == "/" }.size
        val minSpaceToFree = UPDATE_SIZE - remainingSize

        return dirs.sortedBy { it.size }.first { it.size >= minSpaceToFree }.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437L)
    check(part2(testInput) == 24933642L)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

