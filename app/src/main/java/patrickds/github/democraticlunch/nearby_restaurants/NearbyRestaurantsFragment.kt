package patrickds.github.democraticlunch.nearby_restaurants

import android.app.Fragment
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.nearby_restaurants_fragment.*
import kotlinx.android.synthetic.main.nearby_restaurants_fragment.view.*
import patrickds.github.democraticlunch.R
import patrickds.github.democraticlunch.application.DemocraticLunchApplication
import patrickds.github.democraticlunch.extensions.ViewExtensions.showLongSnackBar
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.VotingUpdate
import patrickds.github.democraticlunch.nearby_restaurants.injection.DaggerNearbyRestaurantsComponent
import patrickds.github.democraticlunch.nearby_restaurants.injection.NearbyRestaurantsModule
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

        _nearbyRestaurantsAdapter = NearbyRestaurantsAdapter(mutableListOf())
        _nearbyRestaurantsList = view.nearby_restaurants_list
        _nearbyRestaurantsList.layoutManager = LinearLayoutManager(activity)
        _nearbyRestaurantsList.adapter = _nearbyRestaurantsAdapter

        _swipeRefreshLayout = view as SwipeRefreshLayout
        _swipeRefreshLayout.setOnRefreshListener {
            _presenter.loadNearbyRestaurants()
            _presenter.loadLastChosenRestaurant()
        }

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

    override fun showNearbyRestaurants(restaurants: MutableList<Restaurant>) {
        _swipeRefreshLayout.isRefreshing = false
        _nearbyRestaurantsAdapter.replaceData(restaurants)
    }

    override fun clearItems() {
        _nearbyRestaurantsAdapter.clear()
    }

    override fun showRestaurant(restaurant: Restaurant) {
        _nearbyRestaurantsAdapter.add(restaurant)
    }

    override fun updateVotes(votingUpdate: VotingUpdate) {
        _nearbyRestaurantsAdapter.updateVotes(votingUpdate)
    }

    override fun finishRefreshing() {
        _swipeRefreshLayout.isRefreshing = false
    }

    override fun showLastChosenRestaurant(restaurant: Restaurant) {
        last_chosen_name.text = restaurant.name
    }

    override fun disableVoting() {
        _nearbyRestaurantsAdapter.disable()
    }

    override fun showElectionEndedMessage() {
        election_already_ended_text_view.visibility = View.VISIBLE
    }

    override fun showError(message: String) {
        _swipeRefreshLayout.isRefreshing = false
        view.showLongSnackBar(message)
    }

    override fun showErrorOnLastChosenRestaurant() {
        view.showLongSnackBar("Error downloading last chosen restaurant data")
    }
}