package patrickds.github.democraticlunch.nearby_restaurants

import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.MockitoAnnotations
import patrickds.github.democraticlunch.RxJavaTest
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.usecase.VoteOnRestaurant

class NearbyRestaurantItemPresenterTest : RxJavaTest() {

    @Mock
    lateinit var view: RestaurantItemContract.View

    @Mock
    lateinit var voteOnRestaurant: VoteOnRestaurant

    lateinit var presenter: RestaurantItemPresenter

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        presenter = RestaurantItemPresenter(view, voteOnRestaurant)
    }

    @Test
    fun bindNotVotedRestaurantAndLoadIntoView(){
        val restaurantId = "1"
        val restaurantName = "Outback"
        val restaurantAddress = "221B, Baker Street"
        val restaurantVotes = 0
        val isVoted = false
        val wasChosenThisWeek = false
        val restaurant = Restaurant(
                restaurantId,
                restaurantName,
                restaurantAddress,
                restaurantVotes,
                isVoted,
                wasChosenThisWeek)

        presenter.bind(restaurant)

        verify(view).reset()
        verify(view).showRestaurantName(restaurantName)
        verify(view).showRestaurantAddress(restaurantAddress)
        verify(view).showRestaurantVotes("Votes: $restaurantVotes")
        verify(view).showVoteText()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun bindVotedRestaurantAndLoadIntoView(){
        val restaurantId = "1"
        val restaurantName = "Outback"
        val restaurantAddress = "221B, Baker Street"
        val restaurantVotes = 1
        val isVoted = true
        val wasChosenThisWeek = false
        val restaurant = Restaurant(
                restaurantId,
                restaurantName,
                restaurantAddress,
                restaurantVotes,
                isVoted,
                wasChosenThisWeek)

        presenter.bind(restaurant)

        verify(view).reset()
        verify(view).showRestaurantName(restaurantName)
        verify(view).showRestaurantAddress(restaurantAddress)
        verify(view).showRestaurantVotes("Votes: $restaurantVotes")
        verify(view).showUnVoteText()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun bindAlreadySelectedRestaurantAndLoadIntoView(){
        val restaurantId = "1"
        val restaurantName = "Outback"
        val restaurantAddress = "221B, Baker Street"
        val restaurantVotes = 0
        val isVoted = false
        val wasChosenThisWeek = true
        val restaurant = Restaurant(
                restaurantId,
                restaurantName,
                restaurantAddress,
                restaurantVotes,
                isVoted,
                wasChosenThisWeek)

        presenter.bind(restaurant)

        verify(view).reset()
        verify(view).showRestaurantName(restaurantName)
        verify(view).showRestaurantAddress(restaurantAddress)
        verify(view).showRestaurantVotes("Votes: $restaurantVotes")
        verify(view).showVoteText()
        verify(view).disableVoting()
        verify(view).showAlreadySelectedMessage()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun vote_WhenVotedRestaurant_PresenterUpdatesVoteTextInView(){

        val restaurant = Restaurant("1",
                "Outback",
                "221B, Baker Street",
                1,
                true,
                false)
        val requestValues = VoteOnRestaurant.RequestValues(restaurant)
        val responseValue = VoteOnRestaurant.ResponseValue()

        given(voteOnRestaurant.execute(requestValues))
                .willReturn(Observable.just(responseValue))

        presenter.vote(restaurant)

        verify(view).showUnVoteText()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun vote_WhenNotVotedRestaurant_PresenterUpdatesVoteTextInView(){

        val restaurant = Restaurant("1",
                "Outback",
                "221B, Baker Street",
                0,
                false,
                false)
        val requestValues = VoteOnRestaurant.RequestValues(restaurant)
        val responseValue = VoteOnRestaurant.ResponseValue()

        given(voteOnRestaurant.execute(requestValues))
                .willReturn(Observable.just(responseValue))

        presenter.vote(restaurant)

        verify(view).showVoteText()
        verifyNoMoreInteractions(view)
    }
}