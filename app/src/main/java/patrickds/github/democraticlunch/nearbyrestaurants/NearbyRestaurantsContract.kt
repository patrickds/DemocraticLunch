package patrickds.github.democraticlunch.nearbyrestaurants

import patrickds.github.democraticlunch.nearbyrestaurants.domain.model.Restaurant

class NearbyRestaurantsContract {

    interface View {
        fun showNearbyRestaurants(restaurants: List<Restaurant>)
        fun showError(message: String)
        fun showRestaurant(restaurant: Restaurant)
        fun clearItems()
        fun finishRefreshing()
    }

    interface Presenter {
        fun start()
        fun stop()
        fun loadNearbyRestaurants()
    }
}