package patrickds.github.democraticlunch.nearbyrestaurants

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.nearby_restaurants_activity.*
import patrickds.github.democraticlunch.R
import patrickds.github.democraticlunch.garbage.NotificationUtils
import timber.log.Timber

class NearbyRestaurantsActivity :
        AppCompatActivity(),
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    lateinit var googleApiClient: GoogleApiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nearby_restaurants_activity)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

//        googleApiClient = GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build()

        fab.setOnClickListener {
            NotificationUtils.pickWinner(this)
//            NotificationUtils.remindUser(this)
        }
    }

    override fun onStart() {
        super.onStart()
//        googleApiClient.connect()
    }

    override fun onStop() {
        super.onStop()
//        googleApiClient.disconnect(
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> true
        else -> super.onOptionsItemSelected(item)
    }

    override fun onConnected(p0: Bundle?) {
        val lastLocation = LocationServices
                .FusedLocationApi
                .getLastLocation(googleApiClient)

        Timber.d("${lastLocation.latitude} - ${lastLocation.longitude}")
    }

    override fun onConnectionSuspended(p0: Int) {
        Timber.e("Google play services connection suspended")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Timber.e("Google play services connection failed")
    }
}