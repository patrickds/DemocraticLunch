package patrickds.github.democraticlunch.nearby_restaurants

import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import patrickds.github.democraticlunch.RxJavaTestRunner
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.usecase.GetLastChosenRestaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.usecase.GetNearbyRestaurants
import patrickds.github.democraticlunch.nearby_restaurants.domain.usecase.ListenForVotingUpdates
import patrickds.github.democraticlunch.nearby_restaurants.domain.usecase.VerifyVotingStatus

@RunWith(RxJavaTestRunner::class)
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

        val restaurant = Restaurant("id", "Outback Steak House", "221B, Baker Street", 0, false, false)

        val requestValues = GetNearbyRestaurants.RequestValues(5000)
        given(getNearbyRestaurants.execute(requestValues))
                .willReturn(Observable.just(restaurant))

        presenter.loadNearbyRestaurants()

        val inOrder = Mockito.inOrder(view)
        inOrder.verify(view).clearItems()
        inOrder.verify(view).showRestaurant(restaurant)
    }
}