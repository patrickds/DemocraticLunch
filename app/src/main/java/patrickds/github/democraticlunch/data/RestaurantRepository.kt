package patrickds.github.democraticlunch.data

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import patrickds.github.democraticlunch.BuildConfig
import patrickds.github.democraticlunch.extensions.LocationExtensions.formatToApi
import patrickds.github.democraticlunch.google.places.IGoogleWebService
import patrickds.github.democraticlunch.google.places.ePlaceType
import patrickds.github.democraticlunch.location.LocationService
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IElectionRepository
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IRestaurantRepository
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IVotedRestaurantsDataSource
import javax.inject.Inject

class RestaurantRepository @Inject constructor(
        private val _googleWebService: IGoogleWebService,
        private val _electionRepository: IElectionRepository,
        private val _votedRestaurantsCache: IVotedRestaurantsDataSource,
        private val _locationService: LocationService)
    : IRestaurantRepository {

    override fun getById(id: String): Observable<Restaurant> {

        return _googleWebService.getById(BuildConfig.GOOGLE_WEB_SERVICE_KEY, id)
                .map { it.result!!.toDomain(_votedRestaurantsCache) }
    }

    override fun getNearest(radius: Int): Observable<Restaurant> {
        return Observable.create<Restaurant> { emitter ->

            _electionRepository.getWeekElections()
                    .defaultIfEmpty(listOf())
                    .observeOn(Schedulers.io())
                    .subscribe { elections ->
                        getNearby(radius)
                                .observeOn(Schedulers.io())
                                .subscribe({ restaurant ->

                                    restaurant.wasSelectedThisWeek = elections.any {
                                        it.winner.restaurantId == restaurant.id
                                    }
                                    emitter.onNext(restaurant)
                                }, { error ->
                                    emitter.onError(error)
                                }, {
                                    emitter.onComplete()
                                })
                    }
        }
    }

    fun getNearby(radius: Int): Observable<Restaurant> {

        return _locationService.getLastKnownLocation()
                .flatMap { location ->
                    _googleWebService.getNearbyPlaces(
                            BuildConfig.GOOGLE_WEB_SERVICE_KEY,
                            location.formatToApi(),
                            radius,
                            ePlaceType.RESTAURANT)
                            .subscribeOn(Schedulers.io())
                            .switchMap { Observable.fromIterable(it.results!!) }
                            .map { it.toDomain(_votedRestaurantsCache) }
                }
    }

    override fun clearVoteCache() {
        _votedRestaurantsCache.clear()
    }
}