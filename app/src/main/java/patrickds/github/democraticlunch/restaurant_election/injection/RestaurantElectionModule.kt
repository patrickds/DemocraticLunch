package patrickds.github.democraticlunch.restaurant_election.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import patrickds.github.democraticlunch.restaurant_election.FinishElectionService

@Module
class RestaurantElectionModule(val finishElectionService: FinishElectionService) {

    @Provides
    @RestaurantElectionScope
    fun context(): Context {
        return finishElectionService
    }
}