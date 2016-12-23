package patrickds.github.democraticlunch

import dagger.Component
import patrickds.github.democraticlunch.injection.ApplicationScope
import patrickds.github.democraticlunch.injection.GoogleWebServiceModule
import patrickds.github.democraticlunch.injection.LocationModule
import patrickds.github.democraticlunch.injection.RepositoryModule
import patrickds.github.democraticlunch.nearbyrestaurants.domain.repositories.IRestaurantRepository
import patrickds.github.democraticlunch.nearbyrestaurants.domain.repositories.IVoteRepository
import patrickds.github.democraticlunch.nearbyrestaurants.domain.repositories.IVotedRestaurantRepository
import patrickds.github.democraticlunch.network.IGoogleWebService

@ApplicationScope
@Component(modules = arrayOf(GoogleWebServiceModule::class, RepositoryModule::class, LocationModule::class))
interface DemocraticLunchApplicationComponent {
    val googleWebService: IGoogleWebService
    val votedRestaurantRepository: IVotedRestaurantRepository
    val voteRepository: IVoteRepository
    val restaurantRepository: IRestaurantRepository
}