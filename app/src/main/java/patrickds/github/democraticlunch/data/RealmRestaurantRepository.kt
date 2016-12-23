package patrickds.github.democraticlunch.data

import io.realm.Realm
import patrickds.github.democraticlunch.data.dao.RestaurantDAO
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IVotedRestaurantRepository
import javax.inject.Inject

class RealmRestaurantRepository @Inject constructor() : IVotedRestaurantRepository {

    override fun insertOrUpdate(restaurant: Restaurant) {
        val realm = Realm.getDefaultInstance()

        val restaurantEntity = RestaurantDAO(restaurant.id, restaurant.isVoted)

        realm.executeTransaction {
            it.insertOrUpdate(restaurantEntity)
        }
    }

    override fun getIsVoted(id: String): Boolean {
        val realm = Realm.getDefaultInstance()

        val contains = realm.where(RestaurantDAO::class.java)
                .contains("id", id)
                .count() > 0

        if (!contains)
            return false

        return realm.where(RestaurantDAO::class.java)
                .equalTo("id", id)
                .findFirst()
                .isVoted
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