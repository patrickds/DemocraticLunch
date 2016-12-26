package patrickds.github.democraticlunch.data.dao

import patrickds.github.democraticlunch.nearby_restaurants.domain.model.VoteEntry

class VoteEntryDAO() {

    constructor(restaurantId: String, votes: Int) : this() {
        this.restaurantId = restaurantId
        this.votes = votes
    }

    lateinit var restaurantId: String
    var votes: Int = 0

    fun toVoteEntry() = VoteEntry(restaurantId, votes)

    companion object {
        fun fromDomain(entry: VoteEntry)
                = VoteEntryDAO(entry.restaurantId, entry.votes)
    }
}