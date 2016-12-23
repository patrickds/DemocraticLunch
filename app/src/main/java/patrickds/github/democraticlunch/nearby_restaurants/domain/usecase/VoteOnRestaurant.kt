package patrickds.github.democraticlunch.nearby_restaurants.domain.usecase

import io.reactivex.Observable
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IVotedRestaurantsDataSource
import patrickds.github.democraticlunch.restaurant_election.domain.repositories.IVotingRepository
import javax.inject.Inject

class VoteOnRestaurant
@Inject constructor(
        private val _rankingRepository: IVotingRepository,
        private val _restaurantRepository: IVotedRestaurantsDataSource) {

    fun execute(requestValues: RequestValues): Observable<ResponseValue> {
        val restaurant = requestValues.restaurant
        restaurant.vote()

        _rankingRepository.insertOrUpdate(restaurant)
        _restaurantRepository.insertOrUpdate(restaurant)

        return Observable.just(ResponseValue())
    }

    class RequestValues(val restaurant: Restaurant)
    class ResponseValue()
}
