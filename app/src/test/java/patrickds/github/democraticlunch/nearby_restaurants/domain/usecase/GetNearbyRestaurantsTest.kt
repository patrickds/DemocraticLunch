package patrickds.github.democraticlunch.nearby_restaurants.domain.usecase

import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Matchers
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import patrickds.github.democraticlunch.RestaurantTestHelper
import patrickds.github.democraticlunch.RxJavaTest
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IRestaurantRepository

class GetNearbyRestaurantsTest : RxJavaTest() {

    @Mock
    lateinit var restaurantsRepository: IRestaurantRepository

    lateinit var getNearbyRestaurants: GetNearbyRestaurants

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getNearbyRestaurants = GetNearbyRestaurants(restaurantsRepository)
    }

    @Test
    fun getNearbyRestaurants_WhenThereIsARestaurantEmitIt() {

        val restaurant = RestaurantTestHelper.buildDefaultRestaurant()

        given(restaurantsRepository.getNearest(Matchers.anyInt()))
                .willReturn(Observable.just(restaurant))

        val observer = TestObserver<Restaurant>()

        val requestValues = GetNearbyRestaurants.RequestValues(500)
        getNearbyRestaurants.execute(requestValues)
                .subscribe(observer)

        observer.assertNoErrors()
        observer.assertComplete()
        observer.assertValueCount(1)
        observer.assertValue(restaurant)
    }

    @Test
    fun getNearbyRestaurants_WhenNoRestaurantsShouldNotEmitItems(){

        given(restaurantsRepository.getNearest(Matchers.anyInt()))
                .willReturn(Observable.empty())

        val observer = TestObserver<Restaurant>()

        val requestValues = GetNearbyRestaurants.RequestValues(500)
        getNearbyRestaurants.execute(requestValues)
                .subscribe(observer)

        observer.assertNoErrors()
        observer.assertComplete()
        observer.assertNoValues()
    }
}