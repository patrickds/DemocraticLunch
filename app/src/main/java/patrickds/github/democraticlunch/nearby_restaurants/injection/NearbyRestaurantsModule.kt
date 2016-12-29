package patrickds.github.democraticlunch.nearby_restaurants.injection

import android.app.Activity
import dagger.Module
import dagger.Provides
import patrickds.github.democraticlunch.nearby_restaurants.NearbyRestaurantsContract
import patrickds.github.democraticlunch.nearby_restaurants.NearbyRestaurantsFragment
import patrickds.github.democraticlunch.nearby_restaurants.NearbyRestaurantsPresenter

@Module
class NearbyRestaurantsModule(private val _fragment: NearbyRestaurantsFragment) {

    @Provides
    @NearbyRestaurantsScope
    fun view(): NearbyRestaurantsContract.View {
        return _fragment
    }

    @Provides
    @NearbyRestaurantsScope
    fun activity(): Activity {
        return _fragment.activity
    }

    @Provides
    @NearbyRestaurantsScope
    fun presenter(presenter: NearbyRestaurantsPresenter): NearbyRestaurantsContract.Presenter {
        return presenter
    }
}