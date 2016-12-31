package patrickds.github.democraticlunch.application.injection

import android.content.Context
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.OkHttpDownloader
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
    fun picasso(context: Context, okHttpDownloader: OkHttpDownloader): Picasso {
        //TODO: add cache file
        return Picasso.Builder(context)
                .downloader(okHttpDownloader)
                .build()
    }
}