package patrickds.github.democraticlunch.restaurant_election.domain.usecase

import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Matchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyZeroInteractions
import org.mockito.MockitoAnnotations
import patrickds.github.democraticlunch.RestaurantTestHelper
import patrickds.github.democraticlunch.RxJavaTest
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.VoteEntry
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IElectionRepository
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IRestaurantRepository
import patrickds.github.democraticlunch.restaurant_election.RestaurantElectedNotification
import patrickds.github.democraticlunch.restaurant_election.domain.model.Voting
import patrickds.github.democraticlunch.restaurant_election.domain.repositories.IVotingRepository

class ChooseWinnerAndNotifyUserTest : RxJavaTest() {

    @Mock
    lateinit var votingRepository: IVotingRepository

    @Mock
    lateinit var electionRepository: IElectionRepository

    @Mock
    lateinit var restaurantRepository: IRestaurantRepository

    @Mock
    lateinit var notification: RestaurantElectedNotification

    lateinit var chooseWinnerAndNotifyUser: ChooseWinnerAndNotifyUser

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        chooseWinnerAndNotifyUser = ChooseWinnerAndNotifyUser(
                votingRepository,
                electionRepository,
                restaurantRepository,
                notification)
    }

    @Test
    fun execute_whenNoVotingShouldNotNotifyUserNeitherEndAnyElection() {

        given(votingRepository.getVotingByDay(Matchers.anyInt()))
                .willReturn(Observable.empty())

        chooseWinnerAndNotifyUser.execute()

        verify(votingRepository, Mockito.times(1)).getVotingByDay(Matchers.anyInt())
        verifyZeroInteractions(notification)
        verifyZeroInteractions(electionRepository)
        verifyZeroInteractions(restaurantRepository)
    }

    @Test
    fun execute_whenHasVotingShouldNotifyUserAndCreateElection() {

        val winnerVotes = 3
        val restaurant = RestaurantTestHelper.buildRestaurant(winnerVotes, false)
        val entry1 = VoteEntry("1", 0)
        val entry2 = VoteEntry(restaurant.id, winnerVotes)
        val entries = listOf(entry1, entry2)
        val hasEnded = false

        val voting = Voting(entries, hasEnded)
        val election = voting.electWinner()


        given(votingRepository.getVotingByDay(Matchers.anyInt()))
                .willReturn(Observable.just(voting))

        given(restaurantRepository.getById(restaurant.id))
                .willReturn(Observable.just(restaurant))

        chooseWinnerAndNotifyUser.execute()

        verify(electionRepository, Mockito.times(1)).insertOrUpdate(election)
        verify(votingRepository, Mockito.times(1)).endVoting(Matchers.anyInt())
        verify(restaurantRepository, Mockito.times(1)).clearVoteCache()
//        verify(notification, Mockito.times(1)).notify(restaurantWinner)
    }
}
