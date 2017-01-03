package patrickds.github.democraticlunch.data

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import patrickds.github.democraticlunch.application.DaggerDemocraticLunchApplicationComponent
import patrickds.github.democraticlunch.application.injection.ContextModule
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IRestaurantRepository

@RunWith(AndroidJUnit4::class)
class RestaurantRepositoryIntegrationTest {

    lateinit var restaurantRepository: IRestaurantRepository

    @Before
    fun setup() {

        val context = InstrumentationRegistry.getTargetContext()
        val component = DaggerDemocraticLunchApplicationComponent.builder()
                .contextModule(ContextModule(context))
                .build()

        restaurantRepository = component.restaurantRepository
    }

    @Test
    fun getNearest() {

        val observer = TestObserver<Restaurant>()
        restaurantRepository.getNearest(1000)
                .subscribe(observer)

        observer.await()
        val values = observer.values()
        observer.assertComplete()
        observer.assertNoErrors()
    }
}