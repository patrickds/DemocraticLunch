package patrickds.github.democraticlunch

import android.app.Activity
import android.app.Application
import com.facebook.stetho.Stetho
import timber.log.Timber

class DemocraticLunchApplication : Application() {

    val component: DemocraticLunchApplicationComponent =
            DaggerDemocraticLunchApplicationComponent.builder().build()

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                            .build())

        } else {
            Timber.plant(TimberReleaseTree())
        }
    }

    companion object {
        fun get(activity: Activity) = activity.application as DemocraticLunchApplication
    }
}