package patrickds.github.democraticlunch.restaurant_election

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import java.util.*

object NotificationUtils {

    fun schedule(context: Context) {

        val myIntent = Intent(context, FinishElectionService::class.java)
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getService(context, 0, myIntent, 0)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 6)
        calendar.set(Calendar.MINUTE, 16)
        calendar.set(Calendar.SECOND, 0)

        val fiveMinutesMillis = 1 * 60 * 1000L
        val dayMillis = 24 * 60 * 60 * 1000L

//        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                fiveMinutesMillis,
                pendingIntent)
    }
}
