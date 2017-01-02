package patrickds.github.democraticlunch.application.injection

import android.content.Context
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module(includes = arrayOf(NetworkModule::class, ContextModule::class))
class PicassoModule {

    @Provides
    @ApplicationScope
    fun okHttpDownloader(okHttpClient: OkHttpClient): OkHttp3Downloader {
        return OkHttp3Downloader(okHttpClient)
    }

    @Provides
    @ApplicationScope
    fun picasso(context: Context, okHttpDownloader: OkHttp3Downloader): Picasso {
        return Picasso.Builder(context)
                .downloader(okHttpDownloader)
                .build()
    }
}