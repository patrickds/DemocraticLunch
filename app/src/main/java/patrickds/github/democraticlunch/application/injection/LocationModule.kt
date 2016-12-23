package patrickds.github.democraticlunch.application.injection

import android.content.Context
import android.location.LocationManager
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(ContextModule::class))
class LocationModule {

    @Provides
    @ApplicationScope
    fun locationManager(context: Context): LocationManager {
        return context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
}