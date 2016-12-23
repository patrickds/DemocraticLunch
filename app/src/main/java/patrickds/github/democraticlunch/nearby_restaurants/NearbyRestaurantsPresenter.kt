package patrickds.github.democraticlunch.nearby_restaurants

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import patrickds.github.democraticlunch.nearby_restaurants.domain.usecase.GetLastChosenRestaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.usecase.GetNearbyRestaurants
import javax.inject.Inject

class NearbyRestaurantsPresenter @Inject constructor(
        private val view: NearbyRestaurantsContract.View,
        private val getNearbyRestaurants: GetNearbyRestaurants,
        private val getLastChosenRestaurant: GetLastChosenRestaurant)
    : NearbyRestaurantsContract.Presenter {

    private val _subscriptions = CompositeDisposable()

    override fun start() {
//        loadNearbyRestaurants()
//        loadLastChosenRestaurant()
    }

    override fun stop() {
        _subscriptions.dispose()
    }

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

        _subscriptions.add(subscription)
    }

    override fun loadLastChosenRestaurant() {
        val subscription = getLastChosenRestaurant.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ next ->
                    view.showLastChosenRestaurant(next)
                },{ error ->
                    view.showErrorOnLastChosenRestaurant()
                })

        _subscriptions.add(subscription)
    }
}