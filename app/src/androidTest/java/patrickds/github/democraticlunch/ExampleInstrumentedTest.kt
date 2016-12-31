package patrickds.github.democraticlunch

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import patrickds.github.democraticlunch.google.playservices.GooglePlayServices

/**
 * Instrumentation test, which will execute on an Android device.

 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    @Throws(Exception::class)
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getTargetContext()

        GooglePlayServices(appContext).connect()
                .subscribe { assertTrue(it.succeeded()) }
    }
}
