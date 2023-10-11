import java.util.*

fun main(args: Array<String>) {
    val input = Scanner(System.`in`)

    val player1 = Player(input.nextLine().capitalize())
    val player2 = Player(input.nextLine().capitalize())
    val tennis = Tennis()

    val names = input.nextLine()?.split(',')?.map { it.trim() } ?: emptyList()
    val wins = names.map { namePlayer ->
        namePlayer.capitalize().isValidPlayer(Pair(player1, player2)) { player ->
            player
        }
    }.toTypedArray()

    val outStream = System.out
    System.setOut(System.err)
    val gameState = tennis.computeGameState(player1, player2, wins)
    System.setOut(outStream)

    println(gameState)
}

fun String.capitalize(): String {
    return replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}

fun String.isValidPlayer(players: Pair<Player, Player>, player: (Player?) -> Player?): Player? {
    return if (this == players.first.name) {
        player(players.first)
    } else if (this == players.second.name) {
        player(players.second)
    } else {
        player(null)
    }
}