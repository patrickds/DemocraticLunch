package patrickds.github.democraticlunch.nearbyrestaurants.domain.model

class Restaurant(
        var id: String,
        var name: String,
        var votes: Int,
        var isVoted: Boolean = false) {

    fun vote() {
        if (isVoted) {
            votes--
            isVoted = false
        } else {
            votes++
            isVoted = true
        }
    }

    fun canVote(): Boolean {
        return !isVoted
    }
}
