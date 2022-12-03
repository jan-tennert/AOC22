package challenges

import filterEmptyLines

data class Rucksack(val compartmentOne: String, val compartmentTwo: String)

object Day03: Challenge<List<Rucksack>> {

    override val day = 3

    override val parser = Day03Parser

    override fun solvePartOne(input: List<Rucksack>): Long {
        //filter out the chars of both compartments that are in other rucksacks
        val allChars = input.flatMap { (it.compartmentOne.toSet().toList() + it.compartmentTwo.toSet().toList()).groupingBy { c -> c }.eachCount().filter { c -> c.value > 1 }.keys }
        return allChars.sumOf { it.priority }.toLong()
    }

    override fun solvePartTwo(input: List<Rucksack>): Long {
        val groups = input.map { it.compartmentOne + it.compartmentTwo }.chunked(3)
        val badges = groups.map {
            val string = it.joinToString("") { p -> p.toSet().joinToString("") }
            string.groupingBy { c -> c }.eachCount().filter { c -> c.value > 2 }.keys
        }
        return badges.sumOf { it.sumOf { c -> c.priority } }.toLong()
    }

    private val Char.priority: Int get() {
        val lowercase = 'a'..'z'
        val uppercase = 'A'..'Z'
        return when (this) {
            in lowercase -> lowercase.indexOf(this) + 1
            in uppercase -> uppercase.indexOf(this) + 27
            else -> throw IllegalArgumentException("Unknown character: $this")
        }
    }

}

object Day03Parser: InputParser<List<Rucksack>> {

    override fun parsePartOne(input: String): List<Rucksack> {
        return input.lines().filterEmptyLines().map {
            val compartmentOne = it.substring(0, it.length / 2)
            val compartmentTwo = it.substring(it.length / 2)
            Rucksack(compartmentOne, compartmentTwo)
        }
    }

    override fun parsePartTwo(input: String): List<Rucksack> {
        return parsePartOne(input)
    }

}