package patrickds.github.democraticlunch.nearbyrestaurants.domain.usecase

import io.reactivex.Observable
import patrickds.github.democraticlunch.BuildConfig
import patrickds.github.democraticlunch.nearbyrestaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.network.IGoogleWebService
import javax.inject.Inject

class GetNearbyRestaurants @Inject constructor(
        private val _googleWebService: IGoogleWebService) {

    fun execute(requestValues: RequestValues): Observable<ResponseValue> {
        val placeType = "restaurant"

        return _googleWebService.getNearbyPlaces(
                BuildConfig.GOOGLE_WEB_SERVICE_KEY,
                requestValues.location,
                requestValues.radius,
                requestValues.deviceHasGeoLocation,
                placeType)
                .map { ResponseValue(it.results!!.map { Restaurant(it.name!!) }) }
    }

    class RequestValues(
            val location: String,
            val radius: Int,
            val deviceHasGeoLocation: Boolean)

    class ResponseValue(val restaurants: List<Restaurant>)
}