package challenges

interface InputParser <In> {

    fun parsePartOne(input: String): In

    fun parsePartTwo(input: String): In

}

sealed interface Challenge <In> {

    val day: Int
    val parser: InputParser<In>

    fun solvePartOne(input: In): Long

    fun solvePartTwo(input: In): Long = 0L

}