package patrickds.github.democraticlunch

import android.util.Log
import timber.log.Timber

class TimberReleaseTree : Timber.Tree() {
    companion object {
        val LEVEL_CRITICAL = "critical"
    }

    override fun isLoggable(tag: String?, priority: Int) =
            priority == Log.WARN ||
                    priority == Log.ERROR ||
                    priority == Log.ASSERT

    override fun log(priority: Int, tag: String?, message: String?, t: Throwable?) {
        if (priority == Log.ERROR) {
            when {
//                isNotEmpty(t, message) -> Rollbar.reportException(t, LEVEL_CRITICAL, message)
//                isNotEmpty(message) -> Rollbar.reportMessage(message, LEVEL_CRITICAL)
//                isNotEmpty(t) -> Rollbar.reportException(t, LEVEL_CRITICAL)
            }
        }

        when (priority) {
            Log.ASSERT -> Log.wtf(tag, message, t)
            Log.VERBOSE -> Log.v(tag, message, t)
            Log.DEBUG -> Log.d(tag, message, t)
            Log.ERROR -> Log.e(tag, message, t)
            Log.INFO -> Log.i(tag, message, t)
            Log.WARN -> Log.w(tag, message, t)
        }
    }

    private fun isNotEmpty(t: Throwable?, message: String?) = t != null && message != null
    private fun isNotEmpty(message: String?) = message != null
    private fun isNotEmpty(t: Throwable?) = t != null
}


