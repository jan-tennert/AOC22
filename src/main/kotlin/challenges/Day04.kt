package challenges

import filterEmptyLines

data class Assignment(val sectionOne: IntRange, val sectionTwo: IntRange)

val Day04 = challenge<List<Assignment>>(4) {

    partOne {

        parse {
            it.split("\n").filterEmptyLines().map { line ->
                val ranges = line.split(",").map { section ->
                    val range = section.split("-").map(String::toInt)
                    range[0]..range[1]
                }
                Assignment(ranges[0], ranges[1])
            }
        }

        solve {
            it.sumOf { (sectionOne, sectionTwo) ->
                if (sectionOne in sectionTwo || sectionTwo in sectionOne) 1L else 0L
            }
        }

    }

    partTwo {

        solve {
            it.sumOf { (sectionOne, sectionTwo) ->
                if(sectionOne.intersect(sectionTwo).isEmpty()) 0L else 1L
            }
        }

    }

}

private operator fun IntRange.contains(other: IntRange): Boolean {
    return other.first in this && other.last in this
}