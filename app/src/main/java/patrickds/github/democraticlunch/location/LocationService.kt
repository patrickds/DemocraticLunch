package patrickds.github.democraticlunch.location

import android.location.Location
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import io.reactivex.Observable
import patrickds.github.democraticlunch.google.playservices.GooglePlayServices
import timber.log.Timber
import javax.inject.Inject

open class LocationService
@Inject constructor(private val _playServices: GooglePlayServices) {

    private var lastLocation: Location? = null

    open fun getLastKnownLocation(): Observable<Location> {

        return Observable.create { emitter ->

            _playServices.connect()
                    .subscribe {
                        val locationRequest = LocationRequest.create()
                        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

                        LocationServices.FusedLocationApi.requestLocationUpdates(
                                _playServices.apiClient,
                                locationRequest,
                                {
                                    Timber.e(it.toString())
                                    emitter.onNext(it)
                                })
                    }
        }
    }

    open fun requestLocationUpdates() {

        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        LocationServices.FusedLocationApi.requestLocationUpdates(
                _playServices.apiClient,
                locationRequest,
                { lastLocation = it }
        )
    }
}