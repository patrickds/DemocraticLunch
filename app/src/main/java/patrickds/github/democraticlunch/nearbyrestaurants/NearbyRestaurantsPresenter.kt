package patrickds.github.democraticlunch.nearbyrestaurants

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import patrickds.github.democraticlunch.nearbyrestaurants.domain.usecase.GetNearbyRestaurants
import patrickds.github.democraticlunch.nearbyrestaurants.domain.usecase.VoteOnRestaurant
import timber.log.Timber
import javax.inject.Inject

class NearbyRestaurantsPresenter @Inject constructor(
        private val view: NearbyRestaurantsContract.View,
        private val getNearbyRestaurants: GetNearbyRestaurants,
        private val voteOnRestaurant: VoteOnRestaurant) :
        NearbyRestaurantsContract.Presenter {

    override fun start() {
        val reference = FirebaseDatabase.getInstance().reference
        reference.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
                Timber.d("On cancelled")
            }

            override fun onDataChange(p0: DataSnapshot?) {
                Timber.d("On data changed")
            }
        })
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
                },{
                    view.finishRefreshing()
                })
    }
}