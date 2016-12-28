package patrickds.github.democraticlunch.nearby_restaurants.domain.usecase

import io.reactivex.Observable
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IVotedRestaurantsDataSource
import patrickds.github.democraticlunch.restaurant_election.domain.repositories.IVotingRepository
import javax.inject.Inject

open class VoteOnRestaurant
@Inject constructor(
        private val _votingRepository: IVotingRepository,
        private val _votedRestaurantsDataSource: IVotedRestaurantsDataSource) {

    open fun execute(requestValues: RequestValues): Observable<ResponseValue> {
        val restaurant = requestValues.restaurant
        restaurant.vote()

        _votingRepository.insertOrUpdate(restaurant)
        _votedRestaurantsDataSource.insertOrUpdate(restaurant)

        return Observable.just(ResponseValue())
    }

    data class RequestValues(val restaurant: Restaurant)
    class ResponseValue()
}
