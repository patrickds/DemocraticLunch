package patrickds.github.democraticlunch

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.TrampolineScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.runners.BlockJUnit4ClassRunner

class RxJavaTestRunner(klass: Class<*>) : BlockJUnit4ClassRunner(klass) {

    init {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
        RxJavaPlugins.setInitIoSchedulerHandler { TrampolineScheduler.instance() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { TrampolineScheduler.instance() }
    }
}