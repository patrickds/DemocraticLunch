package patrickds.github.democraticlunch

import android.util.Log
import timber.log.Timber

class TimberReleaseTree : Timber.Tree() {

    override fun isLoggable(tag: String?, priority: Int) =
            priority == Log.WARN ||
                    priority == Log.ERROR ||
                    priority == Log.ASSERT

    override fun log(priority: Int, tag: String?, message: String?, t: Throwable?) {

        // Report to Crashlytics, Rollbar, etc
        when (priority) {
            Log.ASSERT -> Log.wtf(tag, message, t)
            Log.ERROR -> Log.e(tag, message, t)
            Log.WARN -> Log.w(tag, message, t)
        }
    }
}


