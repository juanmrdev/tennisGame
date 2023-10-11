class Tennis {

    companion object {
        const val ADVANTAGE_SCORED = 3
        const val WIN_SCORED = 4
    }

    fun computeGameState(player1: Player, player2: Player, wins: Array<Player?>): String {
        if (wins.contains(null)) GameState.NOTHING

        var gameState: GameState = GameState.NOTHING
        var displayState = ""

        wins.forEach { player ->
            if (player == player1) {
                player1.addPoints()
            } else {
                player2.addPoints()
            }

            when {
                player1.points == player2.points -> {
                    if (player1.checkIsDeuce() || player2.checkIsDeuce()) {
                        gameState = GameState.DEUCE.also {
                            displayState = it.toString()
                        }

                    } else {
                        gameState = GameState.NOTHING
                        displayState = "${player1.points}a"
                    }
                }
                player1.points > player2.points && player1.wonRounds >= ADVANTAGE_SCORED && player2.wonRounds >= ADVANTAGE_SCORED && gameState != GameState.ADVANTAGE -> {
                    if ((player1.wonRounds - player2.wonRounds) == 1) {
                        gameState = GameState.ADVANTAGE.also {
                            displayState = "$player1 $it"
                        }
                    } else if ((player2.wonRounds - player1.wonRounds) == 1) {
                        gameState = GameState.ADVANTAGE.also {
                            displayState = "$player2 $it"
                        }
                    }
                }
                player1.wonRounds >= WIN_SCORED || player2.wonRounds >= WIN_SCORED -> {
                    if ((player1.wonRounds - player2.wonRounds) >= 2) {
                        gameState = GameState.WINS.also {
                            displayState = "$player1 $it"
                        }
                    } else if ((player2.wonRounds - player1.wonRounds) >= 2) {
                        gameState = GameState.WINS.also {
                            displayState = "$player2 $it"
                        }
                    }
                }
                else -> {
                    gameState = GameState.NOTHING
                    displayState = "${player1.nameWithPoints()} - ${player2.nameWithPoints()}"
                }
            }

            println(displayState)
            if (gameState == GameState.WINS) return displayState
        }

        return displayState
    }
}