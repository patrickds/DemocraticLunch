package patrickds.github.democraticlunch.google.places

import org.junit.Assert.assertEquals
import org.junit.Test

class ePlaceTypeTest {

    @Test
    fun toString_WhenTypeIsRestaurant_ShouldReturnLoweCaseRestaurant() {
        val restaurantString = "restaurant"
        val placeType = ePlaceType.RESTAURANT

        assertEquals(restaurantString, placeType.toString())
    }
}