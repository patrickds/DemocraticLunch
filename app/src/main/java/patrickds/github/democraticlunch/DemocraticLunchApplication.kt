package patrickds.github.democraticlunch

import android.app.Activity
import android.app.Application
import android.location.LocationManager
import com.facebook.stetho.Stetho
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import io.realm.Realm
import net.danlew.android.joda.JodaTimeAndroid
import patrickds.github.democraticlunch.injection.ContextModule
import timber.log.Timber

class DemocraticLunchApplication : Application() {

    val component: DemocraticLunchApplicationComponent =
            DaggerDemocraticLunchApplicationComponent.builder()
                    .contextModule(ContextModule(this))
                    .build()

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        JodaTimeAndroid.init(this)
//        NotificationUtils.schedule(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                            .build())

        } else {
            Timber.plant(TimberReleaseTree())
        }

        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val last = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        Timber.d("lastKnownLocation ${last.latitude}, ${last.longitude}")

//        locationManager.requestLocationUpdates(
//                LocationManager.GPS_PROVIDER,
//                LOCATION_REFRESH_TIME,
//                LOCATION_REFRESH_DISTANCE,
//                LocationService())

    }

    companion object {
        fun get(activity: Activity) = activity.application as DemocraticLunchApplication
    }
}