package patrickds.github.democraticlunch.nearby_restaurants

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import patrickds.github.democraticlunch.nearby_restaurants.domain.usecase.GetNearbyRestaurants
import javax.inject.Inject

class NearbyRestaurantsPresenter @Inject constructor(
        private val view: NearbyRestaurantsContract.View,
        private val getNearbyRestaurants: GetNearbyRestaurants) :
        NearbyRestaurantsContract.Presenter {

    override fun start() {
        loadNearbyRestaurants()
    }

    override fun stop() {
    }

    //TODO: unsubscribe
    override fun loadNearbyRestaurants() {

        view.clearItems()

        val requestValues = GetNearbyRestaurants.RequestValues(1000)
        val subscription = getNearbyRestaurants.execute(requestValues)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ next ->
                    view.showRestaurant(next)
                }, { error ->
                    view.showError(error.message!!)
                }, {
                    view.finishRefreshing()
                })
    }
}