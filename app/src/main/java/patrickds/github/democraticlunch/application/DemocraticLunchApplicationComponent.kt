package patrickds.github.democraticlunch.application

import dagger.Component
import patrickds.github.democraticlunch.application.injection.ApplicationScope
import patrickds.github.democraticlunch.application.injection.LocationModule
import patrickds.github.democraticlunch.application.injection.RepositoryModule
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IElectionRepository
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IRestaurantRepository
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IVotedRestaurantRepository
import patrickds.github.democraticlunch.restaurant_election.domain.repositories.IRankingRepository

@ApplicationScope
@Component(modules = arrayOf(RepositoryModule::class, LocationModule::class))
interface DemocraticLunchApplicationComponent {
    val votedRestaurantRepository: IVotedRestaurantRepository
    val restaurantRepository: IRestaurantRepository
    val rankingRepository: IRankingRepository
    val electionRepository: IElectionRepository
}