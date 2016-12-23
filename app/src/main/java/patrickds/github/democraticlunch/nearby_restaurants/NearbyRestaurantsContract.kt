package patrickds.github.democraticlunch.nearby_restaurants

import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant

class NearbyRestaurantsContract {

    interface View {
        fun showNearbyRestaurants(restaurants: MutableList<Restaurant>)
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