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
        fun showRating(rating: String)
        fun loadImageFromUrl(photoUrl: String)
        fun loadImageFromUrlInGrayScale(photoUrl: String)

        fun showVote()
        fun showUnVote()
    }

    interface Presenter {
        fun bind(restaurant: Restaurant)
        fun vote(restaurant: Restaurant)
    }
}


