package patrickds.github.democraticlunch.nearbyrestaurants.domain.repositories

import io.reactivex.Observable
import patrickds.github.democraticlunch.nearbyrestaurants.domain.model.Restaurant

interface IRestaurantRepository {
    fun getNearest(radius: Int): Observable<Restaurant>
}