package patrickds.github.democraticlunch.nearby_restaurants

import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import patrickds.github.democraticlunch.RxJavaTest
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.VoteEntry
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.VotingUpdate
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.eVotingStatus
import patrickds.github.democraticlunch.nearby_restaurants.domain.usecase.GetLastChosenRestaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.usecase.GetNearbyRestaurants
import patrickds.github.democraticlunch.nearby_restaurants.domain.usecase.ListenForVotingUpdates
import patrickds.github.democraticlunch.nearby_restaurants.domain.usecase.VerifyVotingStatus
import patrickds.github.democraticlunch.permissions.PermissionRequester
import patrickds.github.democraticlunch.permissions.ePermission

class NearbyRestaurantsPresenterTest : RxJavaTest() {

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

    @Mock
    lateinit var permissionRequester: PermissionRequester

    lateinit var presenter: NearbyRestaurantsContract.Presenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        presenter = NearbyRestaurantsPresenter(
                view,
                permissionRequester,
                getNearbyRestaurants,
                getLastChosenRestaurant,
                verifyVotingStatus,
                listenForVotingUpdates)
    }

    @Test
    fun verifyVotingStatus_WhenVotingIsOngoing_DoesNothing() {

        given(verifyVotingStatus.execute())
                .willReturn(Observable.just(eVotingStatus.ONGOING))

        presenter.verifyVotingStatus()

        verifyZeroInteractions(view)
    }

    @Test
    fun verifyVotingStatus_WhenVotingHasEnded_DisableVotingAndShowsVotingEndedMessageInView() {

        given(verifyVotingStatus.execute())
                .willReturn(Observable.just(eVotingStatus.ENDED))

        presenter.verifyVotingStatus()

        verify(view).disableVoting()
        verify(view).showElectionEndedMessage()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun verifyVotingStatus_WhenEmitsError_DisableVotingAndShowsErrorInView() {

        given(verifyVotingStatus.execute())
                .willReturn(Observable.error(Exception("Dummy exception")))

        presenter.verifyVotingStatus()

        verify(view).disableVoting()
        verify(view).showVerifyVotingError("Error verifying today's voting status. Voting will be disabled")
        verifyNoMoreInteractions(view)
    }

    @Test
    fun loadNearbyRestaurantsAskingPermission_WhenHasPermissionAndEmitsItem_PresenterShowsItemInView() {

        val restaurant = Restaurant("id", "Outback Steak House", "221B, Baker Street", 0, false, false)

        val requestValues = GetNearbyRestaurants.RequestValues(5000)
        given(getNearbyRestaurants.execute(requestValues))
                .willReturn(Observable.just(restaurant))

        given(permissionRequester.request(ePermission.LOCATION))
                .willReturn(Observable.just(true))

        presenter.loadNearbyRestaurantsAskingPermission()

        val order = inOrder(view, permissionRequester)
        order.verify(permissionRequester).request(ePermission.LOCATION)
        order.verify(view).clearItems()
        order.verify(view).showLoading()
        order.verify(view).showRestaurant(restaurant)
        order.verify(view).hideLoading()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun loadNearbyRestaurantsAskingPermission_WhenDontHavePermissionAndEmitsItem_PresenterDontInteractWithView() {

        val restaurant = Restaurant("id", "Outback Steak House", "221B, Baker Street", 0, false, false)

        val requestValues = GetNearbyRestaurants.RequestValues(5000)
        given(getNearbyRestaurants.execute(requestValues))
                .willReturn(Observable.just(restaurant))

        given(permissionRequester.request(ePermission.LOCATION))
                .willReturn(Observable.just(false))

        presenter.loadNearbyRestaurantsAskingPermission()

        verify(permissionRequester).request(ePermission.LOCATION)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun loadNearbyRestaurantsAskingPermission_WhenHasPermissionAndEmitsError_PresenterShowsErrorInView() {

        val requestValues = GetNearbyRestaurants.RequestValues(5000)
        given(getNearbyRestaurants.execute(requestValues))
                .willReturn(Observable.error(Exception("Dummy exception")))

        given(permissionRequester.request(ePermission.LOCATION))
                .willReturn(Observable.just(true))

        presenter.loadNearbyRestaurantsAskingPermission()

        val order = inOrder(view, permissionRequester)
        order.verify(permissionRequester).request(ePermission.LOCATION)
        order.verify(view).clearItems()
        order.verify(view).showLoading()
        order.verify(view).showError("Error loading nearby restaurants")
        order.verify(view).hideLoading()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun loadLatestChosenRestaurant_WhenEmitsItem_PresenterShowsItemInView() {

        val restaurant = Restaurant("id", "Outback Steak House", "221B, Baker Street", 0, false, false)

        given(getLastChosenRestaurant.execute())
                .willReturn(Observable.just(restaurant))

        presenter.loadLastChosenRestaurant()

        verify(view).showLastChosenRestaurant(restaurant)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun loadLatestChosenRestaurant_WhenEmitsError_PresenterShowsErrorInView() {

        given(getLastChosenRestaurant.execute())
                .willReturn(Observable.error(Exception("Dummy exception")))

        presenter.loadLastChosenRestaurant()

        verify(view).showLastChosenRestaurantError("Error loading last chosen restaurant")
        verifyNoMoreInteractions(view)
    }

    @Test
    fun listenForVotingUpdates_WhenEmitsItem_PresenterShowsItemInView() {

        val votingUpdate = VotingUpdate(listOf(VoteEntry("1", 3)))
        given(listenForVotingUpdates.execute())
                .willReturn(Observable.just(votingUpdate))

        presenter.listenForVotingUpdates()

        verify(view).updateVotes(votingUpdate)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun listenForVotingUpdates_WhenEmitsError_PresenterShowsErrorInView() {
        given(listenForVotingUpdates.execute())
                .willReturn(Observable.error(Exception("Dummy exception")))

        presenter.listenForVotingUpdates()

        verify(view).showVotingUpdatesError("Error listening for voting updates")
        verifyNoMoreInteractions(view)
    }
}