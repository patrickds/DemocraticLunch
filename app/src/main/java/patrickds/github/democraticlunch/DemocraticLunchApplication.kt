package patrickds.github.democraticlunch

import android.app.Activity
import android.app.Application
import com.facebook.stetho.Stetho
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import io.realm.Realm
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber

class DemocraticLunchApplication : Application() {

    val component: DemocraticLunchApplicationComponent =
            DaggerDemocraticLunchApplicationComponent.builder().build()

    override fun onCreate() {
        super.onCreate()

//        NotificationUtils.schedule(this)

        Realm.init(this)
        JodaTimeAndroid.init(this)

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
    }

    companion object {
        fun get(activity: Activity) = activity.application as DemocraticLunchApplication
    }
}