package patrickds.github.democraticlunch.garbage

import android.app.IntentService
import android.content.Intent

class EndElectionService : IntentService("EndRestaurantElectionService") {

    override fun onHandleIntent(intent: Intent) {
        NotificationUtils.pickWinner(this)
    }
}