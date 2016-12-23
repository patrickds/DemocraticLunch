package patrickds.github.democraticlunch.nearby_restaurants.domain.usecase

import io.reactivex.Observable
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IRestaurantRepository
import javax.inject.Inject

class GetNearbyRestaurants
@Inject constructor(private val _restaurantRepository: IRestaurantRepository) {

    fun execute(requestValues: RequestValues): Observable<Restaurant> {
        return _restaurantRepository.getNearest(requestValues.radius)
    }

    class RequestValues(val radius: Int)
    class ResponseValue(val restaurants: List<Restaurant>)
}