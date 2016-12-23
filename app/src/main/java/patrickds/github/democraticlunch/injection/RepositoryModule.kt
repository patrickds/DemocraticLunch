package patrickds.github.democraticlunch.injection

import dagger.Module
import dagger.Provides
import patrickds.github.democraticlunch.data.FirebaseVoteRepository
import patrickds.github.democraticlunch.data.RealmRestaurantRepository
import patrickds.github.democraticlunch.data.RestaurantRepository
import patrickds.github.democraticlunch.nearbyrestaurants.domain.repositories.IRestaurantRepository
import patrickds.github.democraticlunch.nearbyrestaurants.domain.repositories.IVoteRepository
import patrickds.github.democraticlunch.nearbyrestaurants.domain.repositories.IVotedRestaurantRepository

@Module
class RepositoryModule {
    @Provides
    @ApplicationScope
    fun voteRepository(voteRepository: FirebaseVoteRepository): IVoteRepository {
        return voteRepository
    }

    @Provides
    @ApplicationScope
    fun votedRestaurantRepository(votedRestaurantRepository: RealmRestaurantRepository): IVotedRestaurantRepository {
        return votedRestaurantRepository
    }

    @Provides
    @ApplicationScope
    fun restaurantRepository(restaurantRepository: RestaurantRepository): IRestaurantRepository {
        return restaurantRepository
    }
}