package patrickds.github.democraticlunch

import org.junit.Assert.*
import org.junit.Test
import patrickds.github.democraticlunch.nearbyrestaurants.domain.model.Restaurant

class RestaurantTest {

    @Test
    fun voteInRestaurant() {
        val restaurant = Restaurant("10", "Outback", 1, false)

        restaurant.vote()
        assertEquals(2, restaurant.votes)
        assertTrue(restaurant.isVoted)
    }

    @Test
    fun canVote_restaurantAlreadyVotedToday_returnsFalse() {

        val restaurant = Restaurant("10", "Outback", 1, false)
        restaurant.vote()

        assertFalse(restaurant.canVote())
    }
}