package patrickds.github.democraticlunch.application.injection

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import patrickds.github.democraticlunch.BuildConfig
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @Provides
    @ApplicationScope
    fun stethoInterceptor(): StethoInterceptor {
        return StethoInterceptor()
    }

    @Provides
    @ApplicationScope
    fun okHttpClient(stethoInterceptor: StethoInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .apply {
                    if(BuildConfig.DEBUG)
                        addNetworkInterceptor(stethoInterceptor)
                }
                .build()
    }
}