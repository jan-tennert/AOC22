import challenges.Challenge
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.addDefaultResponseValidation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.cookie
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText

const val YEAR = 2022

class AOCApi {

    private val httpClient = HttpClient(CIO) {

        defaultRequest {
            cookie("session", System.getenv("AOC_SESSION"))
        }

        addDefaultResponseValidation()

    }

    suspend fun <T> run(challenge: Challenge<T>, partTwo: Boolean) {
        val input = challenge.parser.parse(inputFor(challenge.day))
        val result = if (partTwo) challenge.solvePartTwo(input) else challenge.solvePartOne(input)
        println("Result for Part ${if(partTwo) 2 else 1} of Day ${challenge.day}: $result")
        //submit(challenge.day, if(partTwo) 2 else 1, result) not working currently
    }

    private suspend fun submit(day: Int, part: Int, answer: Long) {
        val result = httpClient.post("https://adventofcode.com/$YEAR/day/$day/answer") {
            formData {
                append("level", part.toString())
                append("answer", answer.toString())
            }
        }
    }

    private suspend fun downloadInput(day: Int): String {
        val input = httpClient.get("https://adventofcode.com/$YEAR/day/$day/input").bodyAsText()
        val path = Path("challenges", "day$day.txt")
        if (!path.exists()) path.createFile()
        path.writeText(input)
        return input
    }

    private suspend fun inputFor(day: Int): String {
        val path = Path("challenges", "day$day.txt")
        return if (path.exists()) {
            path.readText()
        } else {
            downloadInput(day)
        }
    }

}