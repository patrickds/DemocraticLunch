package patrickds.github.democraticlunch.google.places

import com.squareup.moshi.Moshi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Matchers
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IVotedRestaurantsDataSource

class GooglePlaceDAOTest {

    @Mock
    lateinit var votedRestaurantsCache: IVotedRestaurantsDataSource

    lateinit var moshi: Moshi

    lateinit var placeJson: String

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        moshi = Moshi.Builder().build()

        placeJson = this.javaClass
                .classLoader
                .getResourceAsStream("place.json")
                .reader()
                .readText()
    }

    @Test
    fun serialization() {

        val placeAdapter = moshi.adapter(GooglePlaceDAO::class.java)
        val place = placeAdapter.fromJson(placeJson)

        assertEquals("CoQBdwAAAFsqqJFVXB6kFODSxPs5m5P-NW7rFQ29ULW6J-tsQObl9irheZKNXBn56mRusrPya9Py2FLL88o5xBYlveSqrfh0OF8U83omh8piLa2LWmLtvWToUa3fQoYrSpB4NGd8wMMhnS-cB_MX2IJI0n9j_kwY44Zreuk8lgK5q9MYvntHEhAVfjDMiUdB9kiUWWkH7lOJGhQwpQ1Yr7vpc8rl55C8DR3AYlfVYw", place.photos!!.first().photo_reference)
        assertEquals("Camorra Pizzaria", place.name)
        assertEquals(3.2f, place.rating)
        assertEquals("ChIJAQAAwMOiHpURPTo3NQYYBRE", place.place_id)
        assertEquals("Rua Governador Roberto Silveira, 1194 - Santa Catarina, Caxias do Sul", place.vicinity)
    }

    @Test
    fun toDomain() {
        val id = "1"
        val name = "Outback Steak House"
        val address = "221B, Baker Street"
        val rating = 3.7f

        val photo = PlacePhotoDAO(400, 400, "Reference")
        val photos = listOf(photo)

        val placeDao = GooglePlaceDAO(
                id,
                name,
                address,
                rating,
                photos)

        given(votedRestaurantsCache.getIsVoted(Matchers.anyString()))
                .willReturn(false)

        val restaurant = placeDao.toDomain(votedRestaurantsCache)

        assertEquals(id, restaurant.id)
        assertEquals(name, restaurant.name)
        assertEquals(address, restaurant.address)
        assertEquals(rating, restaurant.rating)
        assertEquals(photo.photo_reference, restaurant.photoReference)
    }
}