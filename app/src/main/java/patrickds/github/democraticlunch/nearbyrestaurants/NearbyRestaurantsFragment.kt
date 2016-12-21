package patrickds.github.democraticlunch.nearbyrestaurants

import android.app.Fragment
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.nearby_restaurants_fragment.view.*
import patrickds.github.democraticlunch.DemocraticLunchApplication
import patrickds.github.democraticlunch.R
import patrickds.github.democraticlunch.extensions.ViewExtensions.showLongSnackBar
import patrickds.github.democraticlunch.nearbyrestaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearbyrestaurants.injection.DaggerNearbyRestaurantsComponent
import patrickds.github.democraticlunch.nearbyrestaurants.injection.NearbyRestaurantsModule
import javax.inject.Inject

class NearbyRestaurantsFragment : Fragment(), NearbyRestaurantsContract.View {

    @Inject
    lateinit var _presenter: NearbyRestaurantsContract.Presenter

    private lateinit var _swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var _nearbyRestaurantsAdapter: NearbyRestaurantsAdapter
    private lateinit var _nearbyRestaurantsList: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.nearby_restaurants_fragment, container, false)

        DaggerNearbyRestaurantsComponent.builder()
                .democraticLunchApplicationComponent(DemocraticLunchApplication.get(activity).component)
                .nearbyRestaurantsModule(NearbyRestaurantsModule(this))
                .build()
                .inject(this)

        _nearbyRestaurantsAdapter = NearbyRestaurantsAdapter(listOf())
        _nearbyRestaurantsList = view.nearby_restaurants_list
        _nearbyRestaurantsList.layoutManager = LinearLayoutManager(activity)
        _nearbyRestaurantsList.adapter = _nearbyRestaurantsAdapter

        _swipeRefreshLayout = view as SwipeRefreshLayout
        _swipeRefreshLayout.setOnRefreshListener { _presenter.loadNearbyRestaurants() }

        return view
    }

    override fun onStart() {
        super.onStart()
        _presenter.start()
    }

    override fun onStop() {
        _presenter.stop()
        super.onStop()
    }

    override fun showNearbyRestaurants(restaurants: List<Restaurant>) {
        _swipeRefreshLayout.isRefreshing = false
        _nearbyRestaurantsAdapter.replaceData(restaurants)
    }

    override fun showError(message: String){
        _swipeRefreshLayout.isRefreshing = false
        view.showLongSnackBar(message)
    }
}