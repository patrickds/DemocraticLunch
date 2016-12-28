package patrickds.github.democraticlunch.nearby_restaurants.domain.usecase

import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.joda.time.LocalDate
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import patrickds.github.democraticlunch.RxJavaTestRunner
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Election
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.VoteEntry
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IElectionRepository
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IRestaurantRepository

@RunWith(RxJavaTestRunner::class)
class GetLastChosenRestaurantTest {

    @Mock
    lateinit var electionRepository: IElectionRepository

    @Mock
    lateinit var restaurantRepository: IRestaurantRepository

    lateinit var getLastChosenRestaurant: GetLastChosenRestaurant

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getLastChosenRestaurant = GetLastChosenRestaurant(
                electionRepository,
                restaurantRepository
        )
    }

    @Test
    fun execute_WhenThereIsAElection_EmitsTheElectedRestaurant() {
        val restaurantId = "1"
        val restaurantVotes = 3
        val entry = VoteEntry(restaurantId, restaurantVotes)
        val electionDate = LocalDate.now()
        val election = Election(electionDate, entry)

        val restaurant = Restaurant(restaurantId,
                "Outback",
                "221B, Baker Street",
                restaurantVotes,
                true,
                false)

        given(electionRepository.getLastElection())
                .willReturn(Observable.just(election))

        given(restaurantRepository.getById(restaurantId))
                .willReturn(Observable.just(restaurant))

        val observer = TestObserver<Restaurant>()
        getLastChosenRestaurant.execute()
                .subscribe(observer)

        observer.assertNoErrors()
        observer.assertValue(restaurant)
        observer.assertValueCount(1)
        observer.assertComplete()
    }

    @Test
    fun execute_WhenThereIsNoElection_EmitsNothing(){

        val restaurantId = "1"
        val restaurantVotes = 3
        val restaurant = Restaurant(restaurantId,
                "Outback",
                "221B, Baker Street",
                restaurantVotes,
                true,
                false)

        given(electionRepository.getLastElection())
                .willReturn(Observable.empty())

        given(restaurantRepository.getById(restaurantId))
                .willReturn(Observable.just(restaurant))

        val observer = TestObserver<Restaurant>()
        getLastChosenRestaurant.execute()
                .subscribe(observer)

        observer.assertNoErrors()
        observer.assertNoValues()
        observer.assertComplete()
    }

    @Test
    fun execute_WhenThereIsAElectionAndThereIsNoRestaurant_EmitsError() {
        val restaurantId = "1"
        val restaurantVotes = 3
        val entry = VoteEntry(restaurantId, restaurantVotes)
        val electionDate = LocalDate.now()
        val election = Election(electionDate, entry)

        given(electionRepository.getLastElection())
                .willReturn(Observable.just(election))

        val error = Exception("There is no restaurant with this id")
        given(restaurantRepository.getById(restaurantId))
                .willReturn(Observable.error(error))

        val observer = TestObserver<Restaurant>()
        getLastChosenRestaurant.execute()
                .subscribe(observer)

        observer.assertNotComplete()
        observer.assertError(error)
    }
}