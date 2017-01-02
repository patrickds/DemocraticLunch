package patrickds.github.democraticlunch.data

import android.location.Location
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import patrickds.github.democraticlunch.RxJavaTest
import patrickds.github.democraticlunch.google.places.*
import patrickds.github.democraticlunch.location.LocationService
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IElectionRepository
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IVotedRestaurantsDataSource

class RestaurantRepositoryTest : RxJavaTest() {

    @Mock
    lateinit var _googleWebService: IGoogleWebService

    @Mock
    lateinit var _electionRepository: IElectionRepository

    @Mock
    lateinit var _votedRestaurantsCache: IVotedRestaurantsDataSource

    @Mock
    lateinit var _locationService: LocationService

    lateinit var _restaurantRepository: RestaurantRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        _restaurantRepository = RestaurantRepository(
                _googleWebService,
                _electionRepository,
                _votedRestaurantsCache,
                _locationService)
    }

    @Test
    fun getNearest_WhenNoWeekElections_ReturnsRestaurantsWithSelectedFalse() {

        val radius = 500

        given(_electionRepository.getWeekElections())
                .willReturn(Observable.empty())

        val location = mock<Location> {
            on { latitude }.thenReturn(51.190021)
            on { longitude }.thenReturn(29.45687)
        }

        given(_locationService.getLastKnownLocation())
                .willReturn(Observable.just(location))

        val firstId = "id1"
        val firstName = "name1"
        val firstAddress = "address1"
        val firstRating = 4.2f
        val firstPhotoReference = "Reference1"

        val secondId = "id2"
        val secondName = "name2"
        val secondAddress = "address2"
        val secondRating = 3.0f
        val secondPhotoReference = "Reference2"

        val results = PlacesResult("OK", listOf(
                GooglePlaceDAO(firstId, firstName, firstAddress, firstRating, listOf(
                        PlacePhotoDAO(400, 500, firstPhotoReference)
                )),
                GooglePlaceDAO(secondId, secondName, secondAddress, secondRating, listOf(
                        PlacePhotoDAO(400, 500, secondPhotoReference)
                ))
        ))

        given(_googleWebService.getNearbyPlaces(any(), any(), any(), eq(ePlaceType.RESTAURANT)))
                .willReturn(Observable.just(results))

        val observer = TestObserver<Restaurant>()
        _restaurantRepository.getNearest(radius)
                .subscribe(observer)

        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertValueCount(2)

        val values = observer.values()

        val first = values[0]
        assertEquals(firstId, first.id)
        assertEquals(firstName, first.name)
        assertEquals(firstAddress, first.address)
        assertEquals(firstRating, first.rating)
        assertFalse(first.wasSelectedThisWeek)
        assertEquals(firstPhotoReference, first.photoReference)

        val second = values[1]
        assertEquals(secondId, second.id)
        assertEquals(secondName, second.name)
        assertEquals(secondAddress, second.address)
        assertEquals(secondRating, second.rating)
        assertFalse(second.wasSelectedThisWeek)
        assertEquals(secondPhotoReference, second.photoReference)
    }
}