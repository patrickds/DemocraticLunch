package patrickds.github.democraticlunch.nearbyrestaurants.injection

import dagger.Module
import dagger.Provides
import patrickds.github.democraticlunch.nearbyrestaurants.NearbyRestaurantsContract
import patrickds.github.democraticlunch.nearbyrestaurants.NearbyRestaurantsPresenter

@Module
class NearbyRestaurantsModule(private val _view: NearbyRestaurantsContract.View) {

    @Provides
    @NearbyRestaurantsScope
    fun view(): NearbyRestaurantsContract.View {
        return _view
    }

    @Provides
    @NearbyRestaurantsScope
    fun presenter(presenter: NearbyRestaurantsPresenter): NearbyRestaurantsContract.Presenter {
        return presenter
    }
}