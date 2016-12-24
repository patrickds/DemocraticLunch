package patrickds.github.democraticlunch.restaurant_election

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import java.util.*

object NotificationUtils {

    fun schedule(context: Context) {

        val intent = Intent(context, FinishElectionService::class.java)
        val pendingIntent = PendingIntent.getService(context, 0, intent, 0)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 12)
        calendar.set(Calendar.MINUTE, 30)
        calendar.set(Calendar.SECOND, 0)

        val dayMillis = 24 * 60 * 60 * 1000L
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                dayMillis,
                pendingIntent)
    }
}
