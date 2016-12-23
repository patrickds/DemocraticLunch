package patrickds.github.democraticlunch.nearby_restaurants.domain.model

class Restaurant(
        var id: String,
        var name: String,
        var address: String,
        var votes: Int,
        var isVoted: Boolean = false,
        var wasSelectedThisWeek: Boolean = false) {

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
