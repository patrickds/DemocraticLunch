package patrickds.github.democraticlunch.nearby_restaurants.injection

import dagger.Module
import dagger.Provides
import patrickds.github.democraticlunch.nearby_restaurants.NearbyRestaurantsContract
import patrickds.github.democraticlunch.nearby_restaurants.NearbyRestaurantsPresenter

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