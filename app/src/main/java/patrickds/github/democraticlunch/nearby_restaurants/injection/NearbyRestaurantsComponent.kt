package patrickds.github.democraticlunch.nearby_restaurants.injection

import dagger.Component
import patrickds.github.democraticlunch.application.DemocraticLunchApplicationComponent
import patrickds.github.democraticlunch.nearby_restaurants.NearbyRestaurantsFragment

@NearbyRestaurantsScope
@Component(modules = arrayOf(NearbyRestaurantsModule::class), dependencies = arrayOf(DemocraticLunchApplicationComponent::class))
interface NearbyRestaurantsComponent {
    fun inject(fragment: NearbyRestaurantsFragment)
}