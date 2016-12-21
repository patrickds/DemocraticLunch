package patrickds.github.democraticlunch.nearbyrestaurants.injection

import dagger.Component
import patrickds.github.democraticlunch.DemocraticLunchApplicationComponent
import patrickds.github.democraticlunch.nearbyrestaurants.NearbyRestaurantsFragment

@NearbyRestaurantsScope
@Component(modules = arrayOf(NearbyRestaurantsModule::class), dependencies = arrayOf(DemocraticLunchApplicationComponent::class))
interface NearbyRestaurantsComponent {
    fun inject(fragment: NearbyRestaurantsFragment)
}