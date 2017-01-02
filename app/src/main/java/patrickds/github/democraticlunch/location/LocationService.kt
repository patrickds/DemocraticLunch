package patrickds.github.democraticlunch.location

import android.location.Location
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import io.reactivex.Observable
import patrickds.github.democraticlunch.google.playservices.GooglePlayServices
import javax.inject.Inject

open class LocationService
@Inject constructor(private val _playServices: GooglePlayServices) {

    open fun getLastKnownLocation(): Observable<Location> =
            _playServices.connect()
                    .flatMap { requestLocationUpdates() }

    private fun requestLocationUpdates() =
            Observable.create<Location> { emitter ->

                val locationRequest = LocationRequest.create()
                        .apply {
                            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                            smallestDisplacement = 0f
                            interval = 0
                        }

                LocationServices.FusedLocationApi.requestLocationUpdates(
                        _playServices.apiClient,
                        locationRequest,
                        { location ->
                            emitter.onNext(location)
                            emitter.onComplete()
                        })
            }
}