package oop

import java.lang.IllegalArgumentException

class Tennis {

    companion object {
        const val ADVANTAGE_SCORED = 3
        const val WIN_SCORED = 4
    }

    fun computeGameState(player1: Player, player2: Player, wins: Array<Player?>): String {
        if (wins.contains(null)) throw IllegalArgumentException("Unknown player in the table scores.")

        var gameState: GameState = GameState.NOTHING
        var displayState = ""

        wins.forEach { player ->
            if (player == player1) {
                player1.addPoints()
            } else {
                player2.addPoints()
            }

            checkGameStates(player1, player2, gameState) { state, string ->
                gameState = state
                displayState = string
            }

            println(displayState)
            if (gameState == GameState.WINS) return displayState
        }

        return displayState
    }

    private fun checkGameStates(player1: Player, player2: Player, gameState: GameState, displayState: (GameState, String) -> Unit) {
        when {
            player1.points == player2.points -> {
                if (player1.checkIsDeuce() || player2.checkIsDeuce()) {
                    displayState(GameState.DEUCE, GameState.DEUCE.toString())

                } else {
                    displayState(GameState.NOTHING, "${player1.points}a")
                }
            }
            player1.wonRounds >= ADVANTAGE_SCORED && player2.wonRounds >= ADVANTAGE_SCORED && gameState != GameState.ADVANTAGE -> {
                if ((player1.wonRounds - player2.wonRounds) == 1) {
                    displayState(GameState.ADVANTAGE, "$player1 ${GameState.ADVANTAGE}")
                } else if ((player2.wonRounds - player1.wonRounds) == 1) {
                    displayState(GameState.ADVANTAGE, "$player2 ${GameState.ADVANTAGE}")
                }
            }
            player1.wonRounds >= WIN_SCORED || player2.wonRounds >= WIN_SCORED -> {
                if ((player1.wonRounds - player2.wonRounds) >= 2) {
                    displayState(GameState.WINS, "$player1 ${GameState.WINS}")
                } else if ((player2.wonRounds - player1.wonRounds) >= 2) {
                    displayState(GameState.WINS, "$player2 ${GameState.WINS}")
                }
            }
            else -> {
                displayState(GameState.NOTHING, "${player1.nameWithPoints()} - ${player2.nameWithPoints()}")
            }
        }
    }
}