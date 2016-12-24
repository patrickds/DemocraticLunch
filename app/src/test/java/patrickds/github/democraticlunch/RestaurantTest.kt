package patrickds.github.democraticlunch

import org.junit.Assert.*
import org.junit.Test
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant

class RestaurantTest {

    @Test
    fun vote_restaurantNotVotes_votes() {
        val restaurant = Restaurant("10", "Outback", "221B, Baker Street", 0, false)

        restaurant.vote()
        assertEquals(1, restaurant.votes)
        assertTrue(restaurant.isVoted)
    }

    @Test
    fun vote_restaurantAlreadyVoted_unvotes() {

        val restaurant = Restaurant("10", "Outback", "221B, Baker Street", 1, true)
        restaurant.vote()

        assertEquals(0, restaurant.votes)
        assertFalse(restaurant.isVoted)
    }

    @Test
    fun canVote_restaurantAlreadyVotedToday_returnsFalse() {

        val restaurant = Restaurant("10", "Outback", "221B, Baker Street", 0, false)
        restaurant.vote()

        assertFalse(restaurant.canVote())
    }
}