package signature

import java.io.File

class Printer(name: String, status: String) {

    private val font1 = font(File("C:\\Users\\user\\Downloads\\roman.txt"))
    private val font2 = font(File("C:\\Users\\user\\Downloads\\medium.txt"))
    private val name = name
    private val status = status

    fun start() {
        if (checkLength(font1) > checkLength(font2)) printOfName(checkLength(font1)) else printOfStatus(checkLength(font2))
    }

    private fun font(file: File): List<MutableList<String>> {
        val path = file.readLines()
        var zapoln = 2
        val (rows, cols) = path[0].split(" ").map { it.toInt() }
        val font = List(rows) { MutableList(cols) {""} }
        for (row in font.indices) {
            val helper = zapoln
            for (col in font[row].indices) {
                font[row][col] = path[zapoln]
                zapoln += rows + 1
            }
            zapoln = helper + 1
            if ((zapoln - 1) % (rows + 1) == 0) break
        }
        return font
    }

    private fun checkLength(font: List<MutableList<String>>): Int {
        var sum = 0
        val word = if (font == font1) name else status
        for (ch in word) {
            sum += if (ch.isUpperCase()) font[0][ch.code - 39].length else if (ch.isLowerCase()) font[0][ch.code - 97].length else when(font) {
                font1 -> 10
                else -> 5
            }
        }
        return sum
    }

    private fun printOfName(length: Int) {
        repeat(length + 8) { print("8") }.also { println() }
        printNameN(font1)
        printStatusN(font2)
        repeat(length + 8) { print("8") }.also { println() }
    }

    private fun printNameN(font: List<MutableList<String>>) {
        for (row in font.indices) {
            print("88  ").also {
                for (ch in name)
                    if (ch != ' ' && ch.isUpperCase()) print(font[row][ch.code - 39]) else if (ch != ' ') print(font[row][ch.code - 97]) else print("          ")
            }.also { println("  88") }
        }
    }

    private fun printStatusN(font: List<MutableList<String>>) {
        val deLength = checkLength(font1) - checkLength(font2)
        val repo = deLength / 2
        for (row in font.indices) {
            print("88  ").also  {
                printo(repo)
                for (ch in status) {
                    if (ch != ' ') print(font[row][ch.code - 39]) else print("     ")
                }
                printo(repo)
            }.also { if (deLength % 2 == 0) println("  88") else println("   88") }
        }
    }

    private fun printOfStatus(length: Int) {
        repeat(length + 8) { print("8") }.also { println() }
        printNameS(font1)
        printStatusS(font2)
        repeat(length + 8) { print("8") }.also { println() }
    }
    private fun printNameS(font: List<MutableList<String>>) {
        val deLength = checkLength(font2) - checkLength(font1)
        val repo = deLength / 2
        for (row in font.indices) {
            print("88  ").also {
                printo(repo)
                for (ch in name) {
                    if (ch != ' ' && ch.isUpperCase()) print(font[row][ch.code - 39]) else if (ch != ' ') print(font[row][ch.code - 97]) else print("          ")
                }
                printo(repo)
            }.also { if (deLength % 2 == 0) println("  88") else println("   88") }
        }
    }

    private fun printStatusS(font: List<MutableList<String>>) {
        for (row in font.indices) {
            print("88  ").also  {
                for (ch in status) {
                    if (ch != ' ') print(font[row][ch.code - 39]) else print("     ")
                }
            }.also { println("  88")  }
        }
    }

    private fun printo(repo: Int) { repeat(repo) { print(" ") } }

}

fun main() {
    Printer(
        print("Enter name and surname: ").run { readln() },
        print("Enter person's status: ").run { readln() }.uppercase()
    ).start()
}