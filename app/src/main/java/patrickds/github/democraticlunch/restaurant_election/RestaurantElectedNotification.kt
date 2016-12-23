package patrickds.github.democraticlunch.restaurant_election

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v7.app.NotificationCompat
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import patrickds.github.democraticlunch.R
import patrickds.github.democraticlunch.nearby_restaurants.NearbyRestaurantsActivity
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IRestaurantRepository
import timber.log.Timber
import javax.inject.Inject

class RestaurantElectedNotification
@Inject constructor(
        private val _context: Context,
        private val _restaurantRepository: IRestaurantRepository) {

    val NOTIFICATION_ID = 3000
    val PENDING_INTENT_ID = 4000

    fun show(restaurantId: String) {

        _restaurantRepository.getById(restaurantId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ restaurant ->
                    notify(restaurant)
                }, { error ->
                    Timber.e(error)
                })
    }

    fun notify(restaurant: Restaurant) {

        val notificationBuilder = NotificationCompat.Builder(_context)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_light)
                .setContentTitle("Let's go lunch?!")
                .setContentText(restaurant.name)
                .setLargeIcon(largeIcon())
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(startActivityIntent())
                .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.priority = Notification.PRIORITY_HIGH
        }

        val notificationManager = _context.
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun startActivityIntent(): PendingIntent {
        val startActivityIntent = Intent(_context, NearbyRestaurantsActivity::class.java)

        return PendingIntent.getActivity(
                _context,
                PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun largeIcon(): Bitmap {
        return BitmapFactory.decodeResource(_context.resources, R.drawable.restaurant)
    }
}