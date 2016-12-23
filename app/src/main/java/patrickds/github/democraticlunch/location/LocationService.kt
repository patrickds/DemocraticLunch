package patrickds.github.democraticlunch.location

import android.location.Location
import android.location.LocationManager
import javax.inject.Inject

class LocationService
@Inject constructor(private val _locationManager: LocationManager) {

    fun getLastKnownLocation(): Location {
        return _locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
    }
}