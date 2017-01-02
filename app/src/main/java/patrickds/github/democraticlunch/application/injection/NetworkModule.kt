package patrickds.github.democraticlunch.application.injection

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import patrickds.github.democraticlunch.BuildConfig
import java.io.File
import java.util.concurrent.TimeUnit

@Module(includes = arrayOf(ContextModule::class))
class NetworkModule {

    @Provides
    @ApplicationScope
    fun stethoInterceptor(): StethoInterceptor {
        return StethoInterceptor()
    }

    @Provides
    @ApplicationScope
    fun cacheFile(context: Context): File{
        return File(context.cacheDir, "okhttp_cache")
    }

    @Provides
    @ApplicationScope
    fun cache(cacheFile: File): Cache{
        val twentyMb = 20 * 1000 * 1000L
        return Cache(cacheFile, twentyMb)
    }

    @Provides
    @ApplicationScope
    fun okHttpClient(stethoInterceptor: StethoInterceptor, cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .cache(cache)
                .apply {
                    if (BuildConfig.DEBUG)
                        addNetworkInterceptor(stethoInterceptor)
                }
                .build()
    }
}