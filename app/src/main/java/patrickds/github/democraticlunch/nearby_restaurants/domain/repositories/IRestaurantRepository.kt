package patrickds.github.democraticlunch.nearby_restaurants.domain.repositories

import io.reactivex.Observable
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant

interface IRestaurantRepository {

    fun getById(id: String): Observable<Restaurant>
    fun getNearest(radius: Int): Observable<Restaurant>
    fun clearVoteCache()
}