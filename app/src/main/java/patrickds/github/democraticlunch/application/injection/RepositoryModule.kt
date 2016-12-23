package patrickds.github.democraticlunch.application.injection

import dagger.Module
import dagger.Provides
import patrickds.github.democraticlunch.data.ElectionRepository
import patrickds.github.democraticlunch.data.VotingRepository
import patrickds.github.democraticlunch.data.VotedRestaurantsCache
import patrickds.github.democraticlunch.data.RestaurantRepository
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IElectionRepository
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IRestaurantRepository
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IVotedRestaurantsDataSource
import patrickds.github.democraticlunch.restaurant_election.domain.repositories.IVotingRepository

@Module(includes = arrayOf(GoogleWebServiceModule::class))
class RepositoryModule {
    @Provides
    @ApplicationScope
    fun votedRestaurantRepository(votedRestaurantRepository: VotedRestaurantsCache): IVotedRestaurantsDataSource {
        return votedRestaurantRepository
    }

    @Provides
    @ApplicationScope
    fun restaurantRepository(restaurantRepository: RestaurantRepository): IRestaurantRepository {
        return restaurantRepository
    }

    @Provides
    @ApplicationScope
    fun rankingRepository(rankingRepository: VotingRepository): IVotingRepository {
        return rankingRepository
    }

    @Provides
    @ApplicationScope
    fun electionRepository(electionRepository: ElectionRepository): IElectionRepository {
        return electionRepository
    }
}