package functional

import oop.GameState
import java.util.*
import kotlin.math.abs

const val ADVANTAGE_SCORED = 3
const val WIN_SCORED = 4

fun main() {
    val input = Scanner(System.`in`)

    val player1 = input.nextLine()
    val player2 = input.nextLine()
    val playedCount = input.nextInt()

    if (input.hasNextLine()) {
        input.nextLine()
    }

    try {
        val wins = Array(playedCount) { input.nextLine() }

        val outStream = System.out
        System.setOut(System.err)
        val gameState: String = computeGameState(player1, player2, wins)
        System.setOut(outStream)
        println(gameState)
    } catch (e: Exception) {
        if(e is IllegalArgumentException) println("${e.message}")
        else println("Something is wrong...")
    }
}

fun computeGameState(player1: String, player2: String, wins: Array<String>): String {
    if (!wins.contains(player1) && !wins.contains(player2)) throw IllegalArgumentException("Unknown player in the table scores.")

    var displayState = ""
    val scores = HashMap<String, MutablePair<Int, Int>>()

    scores[player1] = MutablePair(0, 0)
    scores[player2] = MutablePair(0, 0)


    wins.forEach { player ->
        if (player == player1) {
            scores[player1]?.apply {
                score += if (score < 30) 15 else 10
                wonRounds ++
            }
        } else {
            scores[player2]?.apply {
                score += if (score < 30) 15 else 10
                wonRounds ++
            }
        }

        checkGameStates(player1, player2, scores[player1], scores[player2]) { string ->
            displayState = string
        }

        if (displayState.contains("WINS")) return displayState
    }

    return displayState
}

private fun checkGameStates(
    player1: String,
    player2: String,
    player1Scores: MutablePair<Int, Int>?,
    player2Scores: MutablePair<Int, Int>?,
    displayState: (String) -> Unit
) {

    if (player1Scores == null || player2Scores == null) return

    val checkDeuce = { score: Int -> score >= 40 }
    val advantageMinimumScore =  player1Scores.wonRounds >= ADVANTAGE_SCORED && player2Scores.wonRounds >= ADVANTAGE_SCORED
    val checkAdvantageCondition = { wonRoundsP1: Int, wonRoundsP2: Int ->
        abs(wonRoundsP1 - wonRoundsP2) == 1
    }

    when {
        player1Scores.score == player2Scores.score -> {
            if (checkDeuce(player1Scores.score) || checkDeuce(player2Scores.score)) {
                displayState("DEUCE")
            } else {
                displayState("${player1Scores.score}a")
            }
        }
        advantageMinimumScore && checkAdvantageCondition(player1Scores.wonRounds, player2Scores.wonRounds) -> {
            if ((player1Scores.wonRounds - player2Scores.wonRounds) == 1) {
                displayState("$player1 ${GameState.ADVANTAGE}")
            } else if ((player2Scores.wonRounds - player1Scores.wonRounds) == 1) {
                displayState("$player2 ${GameState.ADVANTAGE}")
            }
        }
        player1Scores.wonRounds >= WIN_SCORED|| player2Scores.wonRounds >= WIN_SCORED-> {
            if ((player1Scores.wonRounds - player2Scores.wonRounds) >= 2) {
                displayState("$player1 ${GameState.WINS}")
            } else if ((player2Scores.wonRounds - player1Scores.wonRounds) >= 2) {
                displayState("$player2 ${GameState.WINS}")
            }
        }
        else -> {
            displayState("$player1 ${player1Scores.score} - $player2 ${player2Scores.score}")
        }
    }
}