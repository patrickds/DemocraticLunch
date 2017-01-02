package patrickds.github.democraticlunch.location

import android.location.Location
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import patrickds.github.democraticlunch.RxJavaInstrumentationTest
import patrickds.github.democraticlunch.google.playservices.GooglePlayServices

@RunWith(AndroidJUnit4::class)
class LocationServiceTest : RxJavaInstrumentationTest() {

    lateinit var locationService: LocationService

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getTargetContext()
        val playServices = GooglePlayServices(context)
        locationService = LocationService(playServices)
    }

    @Test
    fun getLastKnownLocation_WhenHasConnection_ShouldReturnCurrentLocation() {

        val observer = TestObserver<Location>()
        locationService.getLastKnownLocation()
                .subscribe(observer)

        observer.await()
        observer.assertNoErrors()
        observer.assertValueCount(1)
        observer.assertComplete()
    }
}