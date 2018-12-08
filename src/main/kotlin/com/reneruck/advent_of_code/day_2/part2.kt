package com.reneruck.advent_of_code.day_2

import arrow.effects.IO
import java.io.File

/**
 * --- Part Two ---
 * Confident that your list of box IDs is complete, you're ready to find the boxes full of prototype fabric.
 *
 * The boxes will have IDs which differ by exactly one character at the same position in both strings.
 *
 * For example, given the following box IDs:
 *
 * abcde
 * fghij
 * klmno
 * pqrst
 * fguij
 * axcye
 * wvxyz
 *
 * The IDs abcde and axcye are close, but they differ by two characters (the second and fourth).
 * However, the IDs fghij and fguij differ by exactly one character,
 * the third (h and u). Those must be the correct boxes.
 *
 * What letters are common between the two correct box IDs?
 * (In the example above, this is found by removing the differing character from either ID, producing fgij.)
 *
 * @author rruck
 */
object Part2 {

    fun loadInput(fileName: String) = IO { File("src/main/resources/$fileName").readLines() }

    fun diffCharsCount(el1 : String, el2 : String) : Int = el1.zip(el2).map { it.first == it.second }.count { !it }

    fun diff(boxIds : List<String>) = boxIds.fold(mapOf<Tuple<String>, Int>()) { m, id ->
        m.plus(boxIds.map { Pair(Tuple(it, id), diffCharsCount(it, id)) })
    }

    fun sameChars(el1 : String, el2 : String) : String = el1.zip(el2).filter { it.first == it.second }.fold("") { s, c -> s + c.first }

    class Tuple<T>(val el1 : T, val el2 : T) {
        override fun equals(other: Any?): Boolean =
            el1 == (other as Tuple<T>).el1 && el2 == other.el2 || el2 == other.el1 && el2 == other.el1

        override fun hashCode(): Int = el1.hashCode() + el2.hashCode()
        override fun toString(): String = "($el1, $el2)"
    }

    @JvmStatic
    fun main(args: Array<String>) {

        val res = loadInput("day_2/input.txt")
            .attempt()
            .map { it.fold({listOf<String>()},{ it }) }
            .map { diff(it).filter { it.value == 1 } }
            .map { sameChars(it.keys.first().el1, it.keys.first().el2) }
            .unsafeRunSync()


            println(res)
    }
}