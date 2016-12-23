package patrickds.github.democraticlunch.nearby_restaurants.domain.repositories

import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant

interface IVoteRepository {

    fun insertOrUpdate(restaurant: Restaurant)
}