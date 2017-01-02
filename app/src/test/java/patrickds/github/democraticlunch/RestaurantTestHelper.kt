package patrickds.github.democraticlunch

import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant


object RestaurantTestHelper {

    fun buildDefaultRestaurant() =
            buildRestaurant(0)

    fun buildRestaurant(votes: Int, isVoted: Boolean = false, wasSelected: Boolean = false) =
            Restaurant(
                    "1",
                    "Outback",
                    "221B, Baker Street",
                    "Reference",
                    4.2f,
                    votes,
                    isVoted,
                    wasSelected)
}