package patrickds.github.democraticlunch.application.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import patrickds.github.democraticlunch.google.playservices.GooglePlayServices

@Module(includes = arrayOf(ContextModule::class))
class GoogleServicesModule {

    @Provides
    @ApplicationScope
    fun googlePlayServices(context: Context): GooglePlayServices {
        return GooglePlayServices(context)
    }
}