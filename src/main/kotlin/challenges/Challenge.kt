package challenges

interface InputParser <In> {

    fun parsePartOne(input: String): In

    fun parsePartTwo(input: String): In

}

interface Challenge <In> {

    val day: Int
    val parser: InputParser<In>

    fun solvePartOne(input: In): Long

    fun solvePartTwo(input: In): Long = 0L

}

typealias ChallengeSolver <T> = (input: T) -> Long
typealias ChallengeParser <T> = (input: String) -> T

class ChallengeBuilder<T>(private val day: Int) {

    @PublishedApi internal var partOneParser: ChallengeParser<T>? = null
    @PublishedApi internal var partOneSolver: ChallengeSolver<T> = { 0L }

    @PublishedApi internal var partTwoParser: ChallengeParser<T>? = null
    @PublishedApi internal var partTwoSolver: ChallengeSolver<T> = { 0L }

    inline fun partOne(part: PartBuilder.() -> Unit) {
        PartBuilder(true).apply(part)
    }

    inline fun partTwo(part: PartBuilder.() -> Unit) {
        PartBuilder(false).apply(part)
    }

    fun build() = object : Challenge<T> {

        override val day = this@ChallengeBuilder.day

        override val parser = object : InputParser<T> {

            override fun parsePartOne(input: String): T {
                return partOneParser?.invoke(input) ?: throw IllegalStateException("No parser for part one")
            }

            override fun parsePartTwo(input: String): T {
                return partTwoParser?.invoke(input) ?: partOneParser?.invoke(input) ?: throw IllegalStateException("No parser for part two")
            }

        }

        override fun solvePartOne(input: T): Long {
            return partOneSolver(input)
        }

        override fun solvePartTwo(input: T): Long {
            return partTwoSolver(input)
        }

    }

    inner class PartBuilder(@PublishedApi internal val partOne: Boolean) {

        inline fun parse(noinline parser: ChallengeParser<T>) {
            if (partOne) {
                partOneParser = parser
            } else {
                partTwoParser = parser
            }
        }

        inline fun solve(noinline solver: (T) -> Long) {
            if (partOne) {
                partOneSolver = solver
            } else {
                partTwoSolver = solver
            }
        }

    }

}

inline fun <T> challenge(day: Int, challenge: ChallengeBuilder<T>.() -> Unit): Challenge<T> {
    return ChallengeBuilder<T>(day).apply(challenge).build()
}