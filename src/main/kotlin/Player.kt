data class Player(
    val name: String,
    var points: Int = 0,
    var wonRounds: Int = 0
) {
    override fun toString(): String = name
    fun nameWithPoints(): String = "$name $points"
    fun checkIsDeuce(): Boolean = points >= 40
    fun addPoints() {
        wonRounds ++
        points += if (points < 30) {
            15
        } else {
            10
        }
    }
}
