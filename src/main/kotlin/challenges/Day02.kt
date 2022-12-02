package challenges

data class Round(val player: Action, val opponent: Action) {

    val score = when {
        player == opponent -> 3
        player.beats(opponent) -> 6
        else -> 0
    } + player.score

    enum class Action(val score: Int) {
        ROCK(1), PAPER(2), SCISSORS(3);

        fun beats(opponent: Action): Boolean {
            return when (this) {
                ROCK -> opponent == SCISSORS
                PAPER -> opponent == ROCK
                SCISSORS -> opponent == PAPER
            }
        }

    }

}

object Day02 : Challenge<List<Round>> {

    override val day = 2
    override val parser = Day02Parser

    override fun solvePartOne(input: List<Round>): Long {
        return input.sumOf { it.score }.toLong()
    }

}

object Day02Parser : InputParser<List<Round>> {

    override fun parse(input: String): List<Round> {
        return input.split("\n").filter { it.isNotBlank() }.map { round ->
            val actions = round.split(" ").map { action ->
                when(action) {
                    "A" -> Round.Action.ROCK
                    "B" -> Round.Action.PAPER
                    "C" -> Round.Action.SCISSORS
                    "X" -> Round.Action.ROCK
                    "Y" -> Round.Action.PAPER
                    "Z" -> Round.Action.SCISSORS
                    else -> throw IllegalArgumentException("Unknown action: $action")
                }
            }
            Round(actions[0], actions[1])
        }
    }

}