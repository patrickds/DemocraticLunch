package patrickds.github.democraticlunch.nearby_restaurants.domain.repositories

import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant

interface IVotedRestaurantsDataSource {
    fun insertOrUpdate(restaurant: Restaurant)
    fun getIsVoted(id: String): Boolean
    fun clear()
}