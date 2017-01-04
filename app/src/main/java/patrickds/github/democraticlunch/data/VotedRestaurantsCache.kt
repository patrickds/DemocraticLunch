package patrickds.github.democraticlunch.data

import io.realm.Realm
import patrickds.github.democraticlunch.data.dao.RestaurantDAO
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IVotedRestaurantsDataSource
import javax.inject.Inject

class VotedRestaurantsCache @Inject constructor() : IVotedRestaurantsDataSource {

    override fun insertOrUpdate(restaurant: Restaurant) {
        val realm = Realm.getDefaultInstance()

        val restaurantEntity = RestaurantDAO(restaurant.id, restaurant.isVoted, restaurant.votes)

        realm.executeTransaction {
            it.insertOrUpdate(restaurantEntity)
        }
    }

    override fun getById(id: String): RestaurantDAO {
        val realm = Realm.getDefaultInstance()

        val contains = realm.where(RestaurantDAO::class.java)
                .contains("id", id)
                .count() > 0

        if (!contains)
            return RestaurantDAO("", false, 0)

        return realm.where(RestaurantDAO::class.java)
                .equalTo("id", id)
                .findFirst()
    }

    override fun clear() {
        val realm = Realm.getDefaultInstance()

        realm.executeTransaction {
            realm.where(RestaurantDAO::class.java)
                    .findAll()
                    .deleteAllFromRealm()
        }
    }
}