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

    override fun solvePartTwo(input: List<Round>): Long {
        println(input.map { it.score })
        return input.sumOf { it.score }.toLong()
    }

}

object Day02Parser : InputParser<List<Round>> {

    override fun parsePartOne(input: String): List<Round> {
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
            Round(actions[1], actions[0]) //swap it because A..C is the opponent, X..Z is the player
        }
    }

    override fun parsePartTwo(input: String): List<Round> {
        return input.split("\n").filter { it.isNotBlank() }.map { round ->
            val actions = round.split(" ")
            val opponent = when(actions[0]) {
                "A" -> Round.Action.ROCK
                "B" -> Round.Action.PAPER
                "C" -> Round.Action.SCISSORS
                else -> throw IllegalArgumentException("Unknown action")
            }
            val player = when(actions[1]) {
                "X" -> Round.Action.values().first { !it.beats(opponent) }
                "Y" -> opponent
                "Z" -> Round.Action.values().first { it.beats(opponent) }
                else -> throw IllegalArgumentException("Unknown action")
            }
            Round(player, opponent)
        }
    }

}