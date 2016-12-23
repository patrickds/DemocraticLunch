package patrickds.github.democraticlunch.application

import dagger.Component
import patrickds.github.democraticlunch.application.injection.ApplicationScope
import patrickds.github.democraticlunch.application.injection.LocationModule
import patrickds.github.democraticlunch.application.injection.RepositoryModule
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IElectionRepository
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IRestaurantRepository
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IVotedRestaurantsDataSource
import patrickds.github.democraticlunch.restaurant_election.domain.repositories.IVotingRepository

@ApplicationScope
@Component(modules = arrayOf(RepositoryModule::class, LocationModule::class))
interface DemocraticLunchApplicationComponent {
    val votedRestaurantRepository: IVotedRestaurantsDataSource
    val restaurantRepository: IRestaurantRepository
    val rankingRepository: IVotingRepository
    val electionRepository: IElectionRepository
}