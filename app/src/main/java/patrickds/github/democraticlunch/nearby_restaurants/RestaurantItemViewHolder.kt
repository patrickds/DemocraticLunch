package patrickds.github.democraticlunch.nearby_restaurants

import android.support.v7.widget.RecyclerView
import android.view.View
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.GrayscaleTransformation
import kotlinx.android.synthetic.main.nearby_restaurants_list_item.view.*
import patrickds.github.democraticlunch.R
import patrickds.github.democraticlunch.data.VotedRestaurantsCache
import patrickds.github.democraticlunch.data.VotingRepository
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.usecase.VoteOnRestaurant

class RestaurantItemViewHolder(
        private val view: View,
        private val _picasso: Picasso) :
        RecyclerView.ViewHolder(view),
        RestaurantItemContract.View {

    private val _nameTextView by lazy { view.restaurant_name }
    private val _addressTextView by lazy { view.restaurant_address }
    private val _imageView by lazy { view.restaurant_image }
    private val _votesTextView by lazy { view.restaurant_votes }
    private val _ratingTextView by lazy { view.rating_text }
    private val _voteButton by lazy { view.vote_button }
    private val _restaurantAlreadyChosenTextView by lazy { view.restaurant_already_chosen_text }

    private val _presenter: RestaurantItemContract.Presenter

    init {
        _presenter = RestaurantItemPresenter(
                this,
                VoteOnRestaurant(VotingRepository(), VotedRestaurantsCache()))
    }

    fun bind(restaurant: Restaurant) {
        _presenter.bind(restaurant)

        _voteButton.setOnClickListener {
            _presenter.vote(restaurant)
        }

    }

    override fun reset() {
        _restaurantAlreadyChosenTextView.visibility = View.GONE
        _voteButton.visibility = View.VISIBLE
        _voteButton.isClickable = false
        _voteButton.setBackgroundResource(R.color.colorPrimary)
        view.isEnabled = true
    }

    override fun showRestaurantName(name: String) {
        _nameTextView.text = name
    }

    override fun showRestaurantAddress(address: String) {
        _addressTextView.text = address
    }

    override fun showRestaurantVotes(votes: String) {
        _votesTextView.text = votes
    }

    override fun showRating(rating: String) {
        _ratingTextView.text = rating
    }

    override fun loadImageFromUrl(photoUrl: String) {
        _picasso.load(photoUrl)
                .placeholder(R.drawable.placeholder)
                .into(_imageView)
    }

    override fun loadImageFromUrlInGrayScale(photoUrl: String) {
        _picasso.load(photoUrl)
                .placeholder(R.drawable.placeholder)
                .transform(GrayscaleTransformation())
                .into(_imageView)
    }

    override fun disableVoting() {
        _voteButton.isClickable = false
    }

    override fun showAlreadySelectedMessage() {
        _voteButton.visibility = View.GONE
        _restaurantAlreadyChosenTextView.visibility = View.VISIBLE
    }

    override fun showVote() {
//        _voteButton.text = "VOTE"
    }

    override fun showUnVote() {
//        _voteButton.text = "UNVOTE"
    }

    fun disable() {
        view.isEnabled = false
        disableVoting()
    }
}