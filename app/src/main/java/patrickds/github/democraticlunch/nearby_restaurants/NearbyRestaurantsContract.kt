package patrickds.github.democraticlunch.nearby_restaurants

import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.VotingUpdate

class NearbyRestaurantsContract {

    interface View {
        fun showNearbyRestaurants(restaurants: MutableList<Restaurant>)
        fun showError(message: String)
        fun showRestaurant(restaurant: Restaurant)
        fun showLastChosenRestaurant(restaurant: Restaurant)
        fun clearItems()
        fun showLoading()
        fun hideLoading()
        fun showErrorOnLastChosenRestaurant()
        fun disableVoting()
        fun showElectionEndedMessage()
        fun updateVotes(votingUpdate: VotingUpdate)
    }

    interface Presenter {
        fun start()
        fun stop()
        fun loadNearbyRestaurants()
        fun loadLastChosenRestaurant()
        fun verifyVotingStatus()
        fun listenForVotingUpdates()
    }
}