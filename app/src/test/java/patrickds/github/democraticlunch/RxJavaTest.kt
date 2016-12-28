package patrickds.github.democraticlunch

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.TrampolineScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.After
import org.junit.Before

open class RxJavaTest {

    @Before
    fun setupRxJava() {
        resetPlugins()
        setIoSchedulerToCurrentThread()
        setMainThreadSchedulerToCurrentThread()
    }

    @After
    fun tearDownRxJava() {
        resetPlugins()
    }

    private fun setIoSchedulerToCurrentThread() {
        RxJavaPlugins.setInitIoSchedulerHandler { TrampolineScheduler.instance() }
    }

    private fun setMainThreadSchedulerToCurrentThread() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { TrampolineScheduler.instance() }
    }

    private fun resetPlugins() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }
}