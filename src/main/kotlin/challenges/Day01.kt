package challenges

class Day01Input(val numbers: List<Int>)

object Day01 : Challenge<Day01Input> {

    override val day = 1
    override val parser = Day01Parser

    override fun solvePartOne(input: Day01Input): Long {
        return input.numbers.maxOf { it }.toLong()
    }

    override fun solvePartTwo(input: Day01Input): Long {
        return input.numbers.sorted().takeLast(3).sum().toLong()
    }

}

object Day01Parser : InputParser<Day01Input> {

    override fun parsePartOne(input: String): Day01Input {
        return Day01Input(input.split("\n\n").map { it.split("\n").filter { n -> n.isNotBlank() }.sumOf { n -> n.toInt() } })
    }

    override fun parsePartTwo(input: String): Day01Input {
        return parsePartOne(input)
    }

}