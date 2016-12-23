package patrickds.github.democraticlunch.application.injection

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides

@Module
class GsonModule {
    @Provides
    @ApplicationScope
    fun gson(): Gson {
        return GsonBuilder().create()
    }
}