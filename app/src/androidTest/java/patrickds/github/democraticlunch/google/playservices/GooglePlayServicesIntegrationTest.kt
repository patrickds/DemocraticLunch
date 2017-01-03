package patrickds.github.democraticlunch.google.playservices

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GooglePlayServicesIntegrationTest {

    lateinit var playServices: GooglePlayServices

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getTargetContext()
        playServices = GooglePlayServices(context)
    }

    @Test
    fun connect_ShouldReturnConnectionResultSuccess() {

        val observer = TestObserver<eConnectionResult>()
        playServices.connect()
                .subscribe(observer)

        observer.await()
        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertValueCount(1)
        observer.assertValue(eConnectionResult.SUCCESS)
    }
}