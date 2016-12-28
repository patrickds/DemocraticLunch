package patrickds.github.democraticlunch.nearby_restaurants.domain.usecase

import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.joda.time.LocalDate
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import patrickds.github.democraticlunch.RxJavaTest
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.VoteEntry
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.eVotingStatus
import patrickds.github.democraticlunch.restaurant_election.domain.model.Voting
import patrickds.github.democraticlunch.restaurant_election.domain.repositories.IVotingRepository

class VerifyVotingStatusTest : RxJavaTest() {

    @Mock
    lateinit var votingRepository: IVotingRepository

    lateinit var verifyVotingStatus: VerifyVotingStatus

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        verifyVotingStatus = VerifyVotingStatus(votingRepository)
    }

    @Test
    fun execute_WhenVotingIsComplete_EmitsEndedStatus() {

        val entries = listOf(
                VoteEntry("1", 2),
                VoteEntry("2", 0),
                VoteEntry("3", 3),
                VoteEntry("4", 1))
        val voting = Voting(entries, true)

        val today = LocalDate().dayOfYear
        given(votingRepository.getVotingByDay(today))
                .willReturn(Observable.just(voting))

        val observer = TestObserver<eVotingStatus>()
        verifyVotingStatus.execute()
                .subscribe(observer)

        observer.assertValue(eVotingStatus.ENDED)
        observer.assertComplete()
        observer.assertNoErrors()
    }

    @Test
    fun execute_WhenVotingIsNotComplete_EmitsOngoingStatus() {

        val entries = listOf(
                VoteEntry("1", 2),
                VoteEntry("2", 0),
                VoteEntry("4", 1))
        val voting = Voting(entries, false)

        val today = LocalDate().dayOfYear
        given(votingRepository.getVotingByDay(today))
                .willReturn(Observable.just(voting))

        val observer = TestObserver<eVotingStatus>()
        verifyVotingStatus.execute()
                .subscribe(observer)

        observer.assertValue(eVotingStatus.ONGOING)
        observer.assertComplete()
        observer.assertNoErrors()
    }

    @Test
    fun execute_WhenThereIsNoVoting_EmitsNothing() {

        val today = LocalDate().dayOfYear
        given(votingRepository.getVotingByDay(today))
                .willReturn(Observable.empty())

        val observer = TestObserver<eVotingStatus>()
        verifyVotingStatus.execute()
                .subscribe(observer)

        observer.assertNoValues()
        observer.assertNoErrors()
    }

    @Test
    fun execute_WhenErrorGatheringVoting_EmitsError() {

        val exception = Exception("Error gathering votes")
        val today = LocalDate().dayOfYear
        given(votingRepository.getVotingByDay(today))
                .willReturn(Observable.error(exception))

        val observer = TestObserver<eVotingStatus>()
        verifyVotingStatus.execute()
                .subscribe(observer)

        observer.assertError(exception)
        observer.assertNotComplete()
    }
}
