package patrickds.github.democraticlunch.nearby_restaurants.domain.usecase

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import patrickds.github.democraticlunch.RestaurantTestHelper
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
        val restaurant = RestaurantTestHelper.buildDefaultRestaurant()

        val requestValues = VoteOnRestaurant.RequestValues(restaurant)
        voteOnRestaurant.execute(requestValues)

        assertTrue(restaurant.isVoted)
        assertEquals(1, restaurant.votes)
        verify(votingRepository).insertOrUpdate(restaurant)
        verify(votedRestaurantsDataSource).insertOrUpdate(restaurant)
    }

    @Test
    fun execute_WhenRestaurantIsVoted_UnVoteRestaurantUpdateRepoAndUpdateCache(){

        val restaurant = RestaurantTestHelper.buildRestaurant(1, true)

        val requestValues = VoteOnRestaurant.RequestValues(restaurant)
        voteOnRestaurant.execute(requestValues)

        assertFalse(restaurant.isVoted)
        assertEquals(0, restaurant.votes)
        verify(votingRepository).insertOrUpdate(restaurant)
        verify(votedRestaurantsDataSource).insertOrUpdate(restaurant)
    }
}