package patrickds.github.democraticlunch.restaurant_election

import android.app.IntentService
import android.content.Intent
import patrickds.github.democraticlunch.application.DemocraticLunchApplication
import patrickds.github.democraticlunch.restaurant_election.domain.usecase.ChooseWinnerAndNotifyUser
import patrickds.github.democraticlunch.restaurant_election.injection.DaggerRestaurantElectionComponent
import patrickds.github.democraticlunch.restaurant_election.injection.RestaurantElectionModule
import javax.inject.Inject

class FinishElectionService : IntentService("EndRestaurantElectionService") {

    @Inject
    lateinit var chooseWinnerAndNotifyUser: ChooseWinnerAndNotifyUser

    override fun onCreate() {
        super.onCreate()

        DaggerRestaurantElectionComponent.builder()
                .democraticLunchApplicationComponent(DemocraticLunchApplication.get(this).component)
                .restaurantElectionModule(RestaurantElectionModule(this))
                .build()
                .inject(this)
    }

    override fun onHandleIntent(intent: Intent) {
        chooseWinnerAndNotifyUser.execute()
    }
}