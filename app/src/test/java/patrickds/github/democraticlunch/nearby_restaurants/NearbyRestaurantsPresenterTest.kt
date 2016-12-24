package patrickds.github.democraticlunch.nearby_restaurants

import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class NearbyRestaurantsPresenterTest {

    @Mock
    lateinit var view: NearbyRestaurantsContract.View

    lateinit var presenter: NearbyRestaurantsContract.Presenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

//        presenter = NearbyRestaurantsPresenter(view,
//                GetN)
    }
}