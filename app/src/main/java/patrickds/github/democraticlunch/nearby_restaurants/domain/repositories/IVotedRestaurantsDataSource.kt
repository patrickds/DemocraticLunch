package patrickds.github.democraticlunch.nearby_restaurants.domain.repositories

import patrickds.github.democraticlunch.data.dao.RestaurantDAO
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant

interface IVotedRestaurantsDataSource {
    fun insertOrUpdate(restaurant: Restaurant)
    fun getById(id: String): RestaurantDAO
    fun clear()
}