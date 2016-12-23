package patrickds.github.democraticlunch.nearbyrestaurants.domain.repositories

import patrickds.github.democraticlunch.nearbyrestaurants.domain.model.Restaurant

interface IVoteRepository {

    fun insertOrUpdate(restaurant: Restaurant)
}