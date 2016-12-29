package patrickds.github.democraticlunch.location

import android.content.Context
import android.location.Location
import android.os.Bundle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import javax.inject.Inject

class LocationService
@Inject constructor(_context: Context) :
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private val _googleApiClient: GoogleApiClient

    init {

        _googleApiClient = GoogleApiClient.Builder(_context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build()

        _googleApiClient.connect()
    }

    fun getLastKnownLocation(): Location {
        val location = LocationServices
                .FusedLocationApi
                .getLastLocation(_googleApiClient)

        return location
    }

    override fun onConnected(bundle: Bundle?) {

        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        LocationServices.FusedLocationApi.requestLocationUpdates(
                _googleApiClient,
                locationRequest, { location ->
        })
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(result: ConnectionResult) {
    }
}