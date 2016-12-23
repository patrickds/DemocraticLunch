package patrickds.github.democraticlunch.nearbyrestaurants.domain.model

class RestaurantClassification() {

    lateinit var restaurantId: String
    var votes: Int = 0

    constructor(restaurantId: String, votes: Int) : this() {
        this.restaurantId = restaurantId
        this.votes = votes
    }

    fun vote() {
        votes++
    }

    fun unVote() {
        votes--
    }
}