package patrickds.github.democraticlunch.application.injection

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides

@Module
class MoshiModule {
    @Provides
    @ApplicationScope
    fun moshi(): Moshi {
        return Moshi.Builder().build()
    }
}