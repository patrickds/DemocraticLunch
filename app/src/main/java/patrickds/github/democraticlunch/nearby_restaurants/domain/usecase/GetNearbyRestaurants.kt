package patrickds.github.democraticlunch.nearby_restaurants.domain.usecase

import io.reactivex.Observable
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IRestaurantRepository
import javax.inject.Inject

open class GetNearbyRestaurants
@Inject constructor(private val _restaurantRepository: IRestaurantRepository) {

    open fun execute(requestValues: RequestValues): Observable<Restaurant> {
        return _restaurantRepository.getNearest(requestValues.radius)
    }

    data class RequestValues(val radius: Int)
}