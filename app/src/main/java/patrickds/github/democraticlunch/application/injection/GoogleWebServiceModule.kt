package patrickds.github.democraticlunch.application.injection

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import patrickds.github.democraticlunch.BuildConfig
import patrickds.github.democraticlunch.google.places.IGoogleWebService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = arrayOf(NetworkModule::class, GsonModule::class))
class GoogleWebServiceModule {

    @Provides
    @ApplicationScope
    fun googleWebService(retrofit: Retrofit): IGoogleWebService {
        return retrofit.create(IGoogleWebService::class.java)
    }

    @Provides
    @ApplicationScope
    fun retrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(BuildConfig.GOOGLE_WEB_SERVICE_BASE_URL)
                .build()
    }
}
