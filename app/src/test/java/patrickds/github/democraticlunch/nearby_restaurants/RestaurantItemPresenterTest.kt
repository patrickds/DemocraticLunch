package patrickds.github.democraticlunch.nearby_restaurants

import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.MockitoAnnotations
import patrickds.github.democraticlunch.RestaurantTestHelper
import patrickds.github.democraticlunch.RxJavaTest
import patrickds.github.democraticlunch.nearby_restaurants.domain.usecase.VoteOnRestaurant

class RestaurantItemPresenterTest : RxJavaTest() {

    @Mock
    lateinit var view: RestaurantItemContract.View

    @Mock
    lateinit var voteOnRestaurant: VoteOnRestaurant

    lateinit var presenter: RestaurantItemPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = RestaurantItemPresenter(view, voteOnRestaurant)
    }

    @Test
    fun bindNotVotedRestaurantAndLoadIntoView() {

        val restaurant = RestaurantTestHelper.buildDefaultRestaurant()

        presenter.bind(restaurant)

        verify(view).reset()
        verify(view).showRestaurantName(restaurant.name)
        verify(view).showRestaurantAddress(restaurant.address)
        verify(view).showRestaurantVotes("Votes: ${restaurant.votes}")
        verify(view).showRating(restaurant.rating.toString())
        verify(view).showVote()
        verify(view).loadImageFromUrl(restaurant.buildPhotoUrl())
        verifyNoMoreInteractions(view)
    }

    @Test
    fun bindVotedRestaurantAndLoadIntoView() {
        val restaurant = RestaurantTestHelper.buildRestaurant(1, true, false)

        presenter.bind(restaurant)

        verify(view).reset()
        verify(view).showRestaurantName(restaurant.name)
        verify(view).showRestaurantAddress(restaurant.address)
        verify(view).showRestaurantVotes("Votes: ${restaurant.votes}")
        verify(view).showRating(restaurant.rating.toString())
        verify(view).showUnVote()
        verify(view).loadImageFromUrl(restaurant.buildPhotoUrl())
        verifyNoMoreInteractions(view)
    }

    @Test
    fun bindAlreadySelectedRestaurantAndLoadIntoView() {
        val restaurant = RestaurantTestHelper.buildRestaurant(0, false, true)

        presenter.bind(restaurant)

        verify(view).reset()
        verify(view).showRestaurantName(restaurant.name)
        verify(view).showRestaurantAddress(restaurant.address)
        verify(view).showRestaurantVotes("Votes: ${restaurant.votes}")
        verify(view).showRating(restaurant.rating.toString())
        verify(view).showVote()
        verify(view).disableVoting()
        verify(view).showAlreadySelectedMessage()
        verify(view).loadImageFromUrlInGrayScale(restaurant.buildPhotoUrl())
        verifyNoMoreInteractions(view)
    }

    @Test
    fun vote_WhenVotedRestaurant_PresenterUpdatesVoteTextInView() {
        val restaurant = RestaurantTestHelper.buildRestaurant(1, true, false)
        val requestValues = VoteOnRestaurant.RequestValues(restaurant)
        val responseValue = VoteOnRestaurant.ResponseValue()

        given(voteOnRestaurant.execute(requestValues))
                .willReturn(Observable.just(responseValue))

        presenter.vote(restaurant)

        verify(view).showUnVote()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun vote_WhenNotVotedRestaurant_PresenterUpdatesVoteTextInView() {
        val restaurant = RestaurantTestHelper.buildDefaultRestaurant()

        val requestValues = VoteOnRestaurant.RequestValues(restaurant)
        val responseValue = VoteOnRestaurant.ResponseValue()

        given(voteOnRestaurant.execute(requestValues))
                .willReturn(Observable.just(responseValue))

        presenter.vote(restaurant)

        verify(view).showVote()
        verifyNoMoreInteractions(view)
    }
}