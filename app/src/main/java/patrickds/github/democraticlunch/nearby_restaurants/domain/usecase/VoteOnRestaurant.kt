package patrickds.github.democraticlunch.nearby_restaurants.domain.usecase

import io.reactivex.Observable
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IVotedRestaurantRepository
import patrickds.github.democraticlunch.restaurant_election.domain.repositories.IRankingRepository
import javax.inject.Inject

class VoteOnRestaurant
@Inject constructor(
        private val _rankingRepository: IRankingRepository,
        private val _restaurantRepository: IVotedRestaurantRepository) {

    fun execute(requestValues: RequestValues): Observable<ResponseValue> {
        val restaurant = requestValues.restaurant
        restaurant.vote()

        _rankingRepository.update(restaurant)
        _restaurantRepository.insertOrUpdate(restaurant)

        return Observable.just(ResponseValue())
    }

    class RequestValues(val restaurant: Restaurant)
    class ResponseValue()
}
