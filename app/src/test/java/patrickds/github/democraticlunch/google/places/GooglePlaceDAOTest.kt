package patrickds.github.democraticlunch.google.places

import com.squareup.moshi.Moshi
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test


class GooglePlaceDAOTest {

    lateinit var moshi: Moshi

    lateinit var placeJson: String

    @Before
    fun setup() {

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

        assertNotNull(place.name)
        assertNotNull(place.place_id)
        assertNotNull(place.vicinity)
        assertNotNull(place.photos)
    }
}
