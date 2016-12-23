package patrickds.github.democraticlunch.data

import io.reactivex.Observable
import patrickds.github.democraticlunch.BuildConfig
import patrickds.github.democraticlunch.extensions.LocationExtensions.formatToApi
import patrickds.github.democraticlunch.location.LocationService
import patrickds.github.democraticlunch.nearbyrestaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearbyrestaurants.domain.repositories.IRestaurantRepository
import patrickds.github.democraticlunch.nearbyrestaurants.domain.repositories.IVoteRepository
import patrickds.github.democraticlunch.nearbyrestaurants.domain.repositories.IVotedRestaurantRepository
import patrickds.github.democraticlunch.network.IGoogleWebService
import javax.inject.Inject

class RestaurantRepository @Inject constructor(
        private val _googleWebService: IGoogleWebService,
        private val _votedRestaurantRepository: IVotedRestaurantRepository,
        private val _voteRepository: IVoteRepository,
        private val _locationService: LocationService) : IRestaurantRepository {

    override fun getNearest(radius: Int): Observable<Restaurant> {
        val PLACE_TYPE = "restaurant"
        val location = _locationService.getLastKnownLocation()
        val hasSensor = true

        return _googleWebService.getNearbyPlaces(
                BuildConfig.GOOGLE_WEB_SERVICE_KEY,
                location.formatToApi(),
                radius,
                hasSensor,
                PLACE_TYPE)
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
