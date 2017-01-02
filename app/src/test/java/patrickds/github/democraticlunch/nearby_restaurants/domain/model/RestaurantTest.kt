package patrickds.github.democraticlunch.nearby_restaurants.domain.model

import org.junit.Assert.*
import org.junit.Test
import patrickds.github.democraticlunch.BuildConfig

class RestaurantTest {

    @Test
    fun vote_restaurantNotVotes_votes() {
        val restaurant = Restaurant("10", "Outback", "221B, Baker Street", "Reference", 4.2f, 0, false)

        restaurant.vote()
        assertEquals(1, restaurant.votes)
        assertTrue(restaurant.isVoted)
    }

    @Test
    fun vote_restaurantAlreadyVoted_unvotes() {

        val restaurant = Restaurant("10", "Outback", "221B, Baker Street", "Reference", 4.2f, 1, true)
        restaurant.vote()

        assertEquals(0, restaurant.votes)
        assertFalse(restaurant.isVoted)
    }

    @Test
    fun buildPhotoUrl(){
        val restaurant = Restaurant("10", "Outback", "221B, Baker Street", "Reference", 4.2f, 1, true)

        val key = BuildConfig.GOOGLE_WEB_SERVICE_KEY
        val photoUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=500&photoreference=${restaurant.photoReference}&key=$key"

        assertEquals(photoUrl, restaurant.buildPhotoUrl())
    }
}