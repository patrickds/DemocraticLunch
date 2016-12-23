package patrickds.github.democraticlunch.restaurant_election.injection

import dagger.Component
import patrickds.github.democraticlunch.application.DemocraticLunchApplicationComponent
import patrickds.github.democraticlunch.restaurant_election.FinishElectionService

@RestaurantElectionScope
@Component(
        modules = arrayOf(RestaurantElectionModule::class),
        dependencies = arrayOf(DemocraticLunchApplicationComponent::class))
interface RestaurantElectionComponent {

    fun inject(finishElectionService: FinishElectionService)
}