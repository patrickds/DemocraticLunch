package patrickds.github.democraticlunch.data

import io.realm.Realm
import patrickds.github.democraticlunch.data.entities.RestaurantRealm
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IVotedRestaurantRepository
import javax.inject.Inject

class RealmRestaurantRepository @Inject constructor() : IVotedRestaurantRepository {

    override fun insertOrUpdate(restaurant: Restaurant) {
        val realm = Realm.getDefaultInstance()

        val restaurantEntity = RestaurantRealm(restaurant.id, restaurant.isVoted)

        realm.executeTransaction {
            it.insertOrUpdate(restaurantEntity)
        }
    }

    override fun getIsVoted(id: String): Boolean {
        val realm = Realm.getDefaultInstance()

        val contains = realm.where(RestaurantRealm::class.java)
                .contains("id", id)
                .count() > 0

        if (!contains)
            return false

        return realm.where(RestaurantRealm::class.java)
                .equalTo("id", id)
                .findFirst()
                .isVoted
    }

    override fun clear() {
        val realm = Realm.getDefaultInstance()

        realm.executeTransaction {
            realm.where(RestaurantRealm::class.java)
                    .findAll()
                    .deleteAllFromRealm()
        }
    }
}