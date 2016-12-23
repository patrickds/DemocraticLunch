package patrickds.github.democraticlunch.nearby_restaurants.domain.usecase

import io.reactivex.Observable
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IVoteRepository
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IVotedRestaurantRepository
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
