package patrickds.github.democraticlunch.nearby_restaurants

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.eVotingStatus
import patrickds.github.democraticlunch.nearby_restaurants.domain.usecase.GetLastChosenRestaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.usecase.GetNearbyRestaurants
import patrickds.github.democraticlunch.nearby_restaurants.domain.usecase.ListenForVotingUpdates
import patrickds.github.democraticlunch.nearby_restaurants.domain.usecase.VerifyVotingStatus
import patrickds.github.democraticlunch.permissions.PermissionRequester
import patrickds.github.democraticlunch.permissions.ePermission
import timber.log.Timber
import javax.inject.Inject

open class NearbyRestaurantsPresenter
@Inject constructor(
        private val _view: NearbyRestaurantsContract.View,
        private val _permissionRequester: PermissionRequester,
        private val _getNearbyRestaurants: GetNearbyRestaurants,
        private val _getLastChosenRestaurant: GetLastChosenRestaurant,
        private val _verifyVotingStatus: VerifyVotingStatus,
        private val _listenForVotingUpdates: ListenForVotingUpdates)
    : NearbyRestaurantsContract.Presenter {

    private val _subscriptions = CompositeDisposable()

    override fun start() {
        loadNearbyRestaurantsAskingPermission()
        loadLastChosenRestaurant()
        verifyVotingStatus()
        listenForVotingUpdates()
    }

    override fun stop() {
        _subscriptions.clear()
    }

    override fun verifyVotingStatus() {
        val subscription = _verifyVotingStatus.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ value ->
                    if (value == eVotingStatus.ENDED) {
                        _view.disableVoting()
                        _view.showElectionEndedMessage()
                    }
                }, { error ->
                    _view.showVerifyVotingError("Error verifying today's voting status. Voting will be disabled")
                    _view.disableVoting()
                    Timber.e(error)
                })

        _subscriptions.add(subscription)
    }

    override fun listenForVotingUpdates() {
        val subscription = _listenForVotingUpdates.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ value ->
                    _view.updateVotes(value)
                }, { error ->
                    _view.showVotingUpdatesError("Error listening for voting updates")
                    Timber.e(error)
                })

        _subscriptions.add(subscription)
    }

    override fun loadNearbyRestaurantsAskingPermission() {

        _permissionRequester.request(ePermission.LOCATION)
                .subscribe { granted ->
                    if (granted)
                        loadNearbyRestaurants()
                }
    }

    private fun loadNearbyRestaurants() {

        _view.clearItems()
        _view.showLoading()

        val fiveKmRadius = 5000
        val requestValues = GetNearbyRestaurants.RequestValues(fiveKmRadius)
        val subscription = _getNearbyRestaurants.execute(requestValues)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ next ->
                    _view.showRestaurant(next)
                }, { error ->
                    _view.showError("Error loading nearby restaurants")
                    _view.hideLoading()
                    Timber.e(error)
                }, {
                    _view.hideLoading()
                })

        _subscriptions.add(subscription)
    }

    override fun loadLastChosenRestaurant() {
        val subscription = _getLastChosenRestaurant.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ next ->
                    _view.showLastChosenRestaurant(next)
                }, { error ->
                    _view.showLastChosenRestaurantError("Error loading last chosen restaurant")
                    Timber.e(error)
                })

        _subscriptions.add(subscription)
    }
}