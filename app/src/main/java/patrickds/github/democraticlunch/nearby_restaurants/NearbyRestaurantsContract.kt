package patrickds.github.democraticlunch.nearby_restaurants

import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.VotingUpdate

class NearbyRestaurantsContract {

    interface View {
        fun showNearbyRestaurants(restaurants: MutableList<Restaurant>)
        fun showRestaurant(restaurant: Restaurant)
        fun showLastChosenRestaurant(restaurant: Restaurant)
        fun updateVotes(votingUpdate: VotingUpdate)

        fun showElectionEndedMessage()
        fun clearItems()
        fun showLoading()
        fun hideLoading()
        fun disableVoting()

        fun showError(message: String)
        fun showLastChosenRestaurantError(message: String)
        fun showVotingUpdatesError(message: String)
        fun showVerifyVotingError(message: String)
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