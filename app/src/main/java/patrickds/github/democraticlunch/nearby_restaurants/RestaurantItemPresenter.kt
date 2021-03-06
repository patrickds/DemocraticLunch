package patrickds.github.democraticlunch.nearby_restaurants

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.usecase.VoteOnRestaurant
import javax.inject.Inject

class RestaurantItemPresenter
@Inject constructor(
        private val _view: RestaurantItemContract.View,
        private val _voteOnRestaurant: VoteOnRestaurant)
    : RestaurantItemContract.Presenter {

    override fun bind(restaurant: Restaurant) {

        _view.reset()

        _view.showRestaurantName(restaurant.name)
        _view.showRestaurantAddress(restaurant.address)
        _view.showRestaurantVotes("Votes: ${restaurant.votes}")
        _view.showRating(restaurant.rating.toString())

        if (restaurant.isVoted)
            _view.showUnVote()
        else
            _view.showVote()

        if (restaurant.wasSelectedThisWeek) {
            _view.disableVoting()
            _view.showAlreadySelectedMessage()
            _view.loadImageFromUrlInGrayScale(restaurant.buildPhotoUrl())
        }
        else {
            _view.loadImageFromUrl(restaurant.buildPhotoUrl())
        }
    }

    override fun vote(restaurant: Restaurant) {
        _voteOnRestaurant.execute(VoteOnRestaurant.RequestValues(restaurant))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, {}, {
                    if (restaurant.isVoted)
                        _view.showUnVote()
                    else
                        _view.showVote()
                })
    }
}