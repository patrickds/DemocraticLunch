package patrickds.github.democraticlunch.nearbyrestaurants.domain.usecase

import io.reactivex.Observable
import patrickds.github.democraticlunch.nearbyrestaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearbyrestaurants.domain.repositories.IVotedRestaurantRepository
import patrickds.github.democraticlunch.nearbyrestaurants.domain.repositories.IVoteRepository
import javax.inject.Inject

class VoteOnRestaurant
@Inject constructor(val voteRepository: IVoteRepository, val restaurantRepository: IVotedRestaurantRepository) {

    fun execute(requestValues: RequestValues): Observable<ResponseValue> {
        val restaurant = requestValues.restaurant
        restaurant.vote()
        voteRepository.insertOrUpdate(restaurant)
        restaurantRepository.insertOrUpdate(restaurant)

        return Observable.just(ResponseValue())
    }

    class RequestValues(val restaurant: Restaurant)
    class ResponseValue()
}
