package patrickds.github.democraticlunch.nearby_restaurants

import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant

class NearbyRestaurantsContract {

    interface View {
        fun showNearbyRestaurants(restaurants: MutableList<Restaurant>)
        fun showError(message: String)
        fun showRestaurant(restaurant: Restaurant)
        fun showLastChosenRestaurant(restaurant: Restaurant)
        fun clearItems()
        fun finishRefreshing()
        fun showErrorOnLastChosenRestaurant()
    }

    interface Presenter {
        fun start()
        fun stop()
        fun loadNearbyRestaurants()
        fun loadLastChosenRestaurant()
    }
}