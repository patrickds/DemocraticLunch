package patrickds.github.democraticlunch.nearby_restaurants.domain.usecase

import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import patrickds.github.democraticlunch.RxJavaTestRunner
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.VoteEntry
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.VotingUpdate
import patrickds.github.democraticlunch.restaurant_election.domain.repositories.IVotingRepository

@RunWith(RxJavaTestRunner::class)
class ListenForVotingUpdatesTest {

    @Mock
    lateinit var votingRepository: IVotingRepository

    lateinit var listenForVotingUpdates: ListenForVotingUpdates

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        listenForVotingUpdates = ListenForVotingUpdates(votingRepository)
    }

    @Test
    fun execute_WhenVoteHappens_EmitsVotes() {

        val votingUpdates = listOf(
                VotingUpdate(listOf(VoteEntry("1", 2))),
                VotingUpdate(listOf(VoteEntry("1", 3))),
                VotingUpdate(listOf(VoteEntry("2", 1)))
        )

        given(votingRepository.listenForVotingUpdates())
                .willReturn(Observable.fromIterable(votingUpdates))

        val observer = TestObserver<VotingUpdate>()
        listenForVotingUpdates.execute()
                .subscribe(observer)

        observer.assertValueCount(3)
        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertValues(*votingUpdates.toTypedArray())
    }

    @Test
    fun execute_WhenNoVoteHappens_EmitsNothing() {

        given(votingRepository.listenForVotingUpdates())
                .willReturn(Observable.empty())

        val observer = TestObserver<VotingUpdate>()
        listenForVotingUpdates.execute()
                .subscribe(observer)

        observer.assertNoValues()
        observer.assertNoErrors()
    }

    @Test
    fun execute_WhenError_EmitsError(){

        val error = Exception("Error")
        given(votingRepository.listenForVotingUpdates())
                .willReturn(Observable.error(error))

        val observer = TestObserver<VotingUpdate>()
        listenForVotingUpdates.execute()
                .subscribe(observer)

        observer.assertError(error)
    }
}