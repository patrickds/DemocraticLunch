package patrickds.github.democraticlunch.nearbyrestaurants.domain.repositories

import patrickds.github.democraticlunch.nearbyrestaurants.domain.model.Restaurant

interface IVotedRestaurantRepository {
    fun insertOrUpdate(restaurant: Restaurant)
    fun getIsVoted(id: String): Boolean
    fun clear()
}