package patrickds.github.democraticlunch.nearbyrestaurants

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import patrickds.github.democraticlunch.nearbyrestaurants.domain.usecase.GetNearbyRestaurants
import javax.inject.Inject

class NearbyRestaurantsPresenter @Inject constructor(
        private val view: NearbyRestaurantsContract.View,
        private val getNearbyRestaurants: GetNearbyRestaurants) :
        NearbyRestaurantsContract.Presenter {

    override fun start() {
    }

    override fun stop() {
    }

    override fun loadNearbyRestaurants() {
        //TODO: unsubscribe
        val requestValues = GetNearbyRestaurants.RequestValues(
                "-29.1882154,-51.2160719",
                1000,
                true)

        val subscription = getNearbyRestaurants.execute(requestValues)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    view.showNearbyRestaurants(response.restaurants)
                }, { error ->
                    view.showError("Error loading restaurants")
                })
    }
}