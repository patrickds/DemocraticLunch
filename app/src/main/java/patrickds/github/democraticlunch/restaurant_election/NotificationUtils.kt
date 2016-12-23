package patrickds.github.democraticlunch.restaurant_election

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v7.app.NotificationCompat
import patrickds.github.democraticlunch.R
import patrickds.github.democraticlunch.nearby_restaurants.NearbyRestaurantsActivity
import java.util.*

object NotificationUtils {

    val NOTIFICATION_ID = 3000
    val PENDING_INTENT_ID = 4000

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

    fun remindUser(context: Context, winnerId: String) {

        val notificationBuilder = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_light)
                .setContentTitle("Winner Chosen!!!")
                .setContentText(winnerId)
                .setLargeIcon(largeIcon(context))
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(createPendingIntent(context))
                .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.priority = Notification.PRIORITY_HIGH
        }

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    fun createPendingIntent(context: Context): PendingIntent {
        val startActivityIntent = Intent(context, NearbyRestaurantsActivity::class.java)

        return PendingIntent.getActivity(
                context,
                PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun largeIcon(context: Context): Bitmap {
        return BitmapFactory.decodeResource(context.resources, R.drawable.restaurant)
    }
}
