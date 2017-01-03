package patrickds.github.democraticlunch.google.places

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import patrickds.github.democraticlunch.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class GoogleWebServiceIntegrationTest {

    lateinit var webService: IGoogleWebService

    @Before
    fun setup() {

        val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.GOOGLE_WEB_SERVICE_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        webService = retrofit.create(IGoogleWebService::class.java)
    }

    @Test
    fun test() {

        val observer = TestObserver<PlacesResult>()

        webService.getNearbyPlaces(
                BuildConfig.GOOGLE_WEB_SERVICE_KEY,
                "-29.15751135,-51.1944663",
                1000,
                ePlaceType.RESTAURANT)
                .subscribe(observer)

        observer.await()
        observer.assertComplete()
        observer.assertNoErrors()
    }
}