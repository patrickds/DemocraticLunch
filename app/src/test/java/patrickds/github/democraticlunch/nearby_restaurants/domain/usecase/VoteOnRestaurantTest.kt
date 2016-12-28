package patrickds.github.democraticlunch.nearby_restaurants.domain.usecase

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IVotedRestaurantsDataSource
import patrickds.github.democraticlunch.restaurant_election.domain.repositories.IVotingRepository

class VoteOnRestaurantTest {

    @Mock
    lateinit var votingRepository: IVotingRepository

    @Mock
    lateinit var votedRestaurantsDataSource: IVotedRestaurantsDataSource

    lateinit var voteOnRestaurant: VoteOnRestaurant

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        voteOnRestaurant = VoteOnRestaurant(
                votingRepository,
                votedRestaurantsDataSource
        )
    }

    @Test
    fun execute_WhenRestaurantIsNotVoted_VoteOnRestaurantUpdateRepoAndInsertIntoCache() {
        val restaurant = Restaurant("id",
                "Outback",
                "221B, Baker Street",
                2,
                false,
                false)

        val requestValues = VoteOnRestaurant.RequestValues(restaurant)
        voteOnRestaurant.execute(requestValues)

        assertTrue(restaurant.isVoted)
        assertEquals(3, restaurant.votes)
        verify(votingRepository).insertOrUpdate(restaurant)
        verify(votedRestaurantsDataSource).insertOrUpdate(restaurant)
    }

    @Test
    fun execute_WhenRestaurantIsVoted_UnVoteRestaurantUpdateRepoAndUpdateCache(){

        val restaurant = Restaurant("id",
                "Outback",
                "221B, Baker Street",
                3,
                true,
                true)

        val requestValues = VoteOnRestaurant.RequestValues(restaurant)
        voteOnRestaurant.execute(requestValues)

        assertFalse(restaurant.isVoted)
        assertEquals(2, restaurant.votes)
        verify(votingRepository).insertOrUpdate(restaurant)
        verify(votedRestaurantsDataSource).insertOrUpdate(restaurant)
    }
}