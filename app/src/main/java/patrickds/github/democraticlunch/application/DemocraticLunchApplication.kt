package patrickds.github.democraticlunch.application

import android.app.Activity
import android.app.Application
import android.app.Service
import com.facebook.stetho.Stetho
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import io.realm.Realm
import net.danlew.android.joda.JodaTimeAndroid
import patrickds.github.democraticlunch.BuildConfig
import patrickds.github.democraticlunch.application.injection.ContextModule
import patrickds.github.democraticlunch.restaurant_election.NotificationUtils
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
        NotificationUtils.schedule(this)

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
        fun get(service: Service) = service.application as DemocraticLunchApplication
    }
}