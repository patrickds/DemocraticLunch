package patrickds.github.democraticlunch.nearby_restaurants

import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.usecase.GetLastChosenRestaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.usecase.GetNearbyRestaurants
import patrickds.github.democraticlunch.nearby_restaurants.domain.usecase.ListenForVotingUpdates
import patrickds.github.democraticlunch.nearby_restaurants.domain.usecase.VerifyVotingStatus

class NearbyRestaurantsPresenterTest {

    @Mock
    lateinit var view: NearbyRestaurantsContract.View

    @Mock
    lateinit var getNearbyRestaurants: GetNearbyRestaurants

    @Mock
    lateinit var getLastChosenRestaurant: GetLastChosenRestaurant

    @Mock
    lateinit var verifyVotingStatus: VerifyVotingStatus

    @Mock
    lateinit var listenForVotingUpdates: ListenForVotingUpdates

    lateinit var presenter: NearbyRestaurantsContract.Presenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        presenter = NearbyRestaurantsPresenter(
                view,
                getNearbyRestaurants,
                getLastChosenRestaurant,
                verifyVotingStatus,
                listenForVotingUpdates)
    }

    @Test
    fun testGetNearbyRestaurantsAndLoadIntoView() {

        val requestValues = GetNearbyRestaurants.RequestValues(1000)
        val restaurant = Restaurant("id", "Outback Steak House", "221B, Baker Street", 0, false, false)

        given(getNearbyRestaurants.execute(requestValues)).willReturn(Observable.just(restaurant))

        `when`(getNearbyRestaurants.execute(requestValues))

        presenter.loadNearbyRestaurants()

        verify(view).showRestaurant(restaurant)
    }
}