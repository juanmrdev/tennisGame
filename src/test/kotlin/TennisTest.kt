import oop.Player
import oop.Tennis
import org.testng.annotations.Test

class TennisTest {

    @Test
    fun testTennisGame() {
        val player1 =  Player("Bob")
        val player2 =  Player("Anna")
        val wins: Array<Player?> = arrayOf(
            player1,
            player2,
            player1
        )
        val tennis = Tennis()

        val score = tennis.computeGameState(player1, player2, wins)

        assert(score == "Bob 30 - Anna 15")

    }

}