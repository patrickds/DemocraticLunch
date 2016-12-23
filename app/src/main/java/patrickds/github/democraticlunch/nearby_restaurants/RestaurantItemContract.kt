package patrickds.github.democraticlunch.nearby_restaurants

import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant

class RestaurantItemContract {
    interface View {
        fun reset()
        fun disableVoting()
        fun showAlreadySelectedMessage()
        fun showRestaurantName(name: String)
        fun showRestaurantAddress(address: String)
        fun showRestaurantVotes(votes: String)

        fun showVoteText()
        fun showUnVoteText()
    }

    interface Presenter {
        fun bind(restaurant: Restaurant)
        fun vote(restaurant: Restaurant)
    }
}


