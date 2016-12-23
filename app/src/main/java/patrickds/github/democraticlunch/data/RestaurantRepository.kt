package patrickds.github.democraticlunch.data

import io.reactivex.Observable
import patrickds.github.democraticlunch.BuildConfig
import patrickds.github.democraticlunch.nearbyrestaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearbyrestaurants.domain.repositories.IRestaurantRepository
import patrickds.github.democraticlunch.nearbyrestaurants.domain.repositories.IVoteRepository
import patrickds.github.democraticlunch.nearbyrestaurants.domain.repositories.IVotedRestaurantRepository
import patrickds.github.democraticlunch.network.IGoogleWebService
import javax.inject.Inject

class RestaurantRepository @Inject constructor(
        private val _googleWebService: IGoogleWebService,
        private val _votedRestaurantRepository: IVotedRestaurantRepository,
        private val _voteRepository: IVoteRepository) : IRestaurantRepository {

    override fun getNearest(radius: Int): Observable<Restaurant> {
        val placeType = "restaurant"
        val location = "-29.1882154,-51.2160719"
        val hasSensor = true

        return _googleWebService.getNearbyPlaces(
                BuildConfig.GOOGLE_WEB_SERVICE_KEY,
                location,
                radius,
                hasSensor,
                placeType)
                .switchMap { Observable.fromIterable(it.results!!) }
                .map {
                    val id = it.id!!
                    val name = it.name!!
                    val votes = 0
                    val isVoted = _votedRestaurantRepository.getIsVoted(id)
                    Restaurant(id, name, votes, isVoted)
                }
    }
}
