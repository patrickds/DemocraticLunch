package patrickds.github.democraticlunch.nearby_restaurants

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.nearby_restaurants_list_item.view.*
import patrickds.github.democraticlunch.data.VotedRestaurantsCache
import patrickds.github.democraticlunch.data.VotingRepository
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.usecase.VoteOnRestaurant

class RestaurantItemViewHolder(val view: View) :
        RecyclerView.ViewHolder(view),
        RestaurantItemContract.View {

    private val _nameTextView by lazy { view.restaurant_name }
    private val _votesTextView by lazy { view.restaurant_votes }
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
        _voteButton.isEnabled = true
        view.isEnabled = true
    }

    override fun showRestaurantName(name: String) {
        _nameTextView.text = name
    }

    override fun showRestaurantVotes(votes: String) {
        _votesTextView.text = votes
    }

    override fun disableVoting() {
        _voteButton.isEnabled = false
        view.isEnabled = false
    }

    override fun showAlreadySelectedMessage() {
        _voteButton.visibility = View.GONE
        _restaurantAlreadyChosenTextView.visibility = View.VISIBLE
    }

    override fun showVoteText() {
        _voteButton.text = "VOTE"
    }

    override fun showUnVoteText() {
        _voteButton.text = "UNVOTE"
    }

    fun disable() {
        view.isEnabled = false
        _voteButton.isEnabled = false
        _nameTextView.isEnabled = false
        _votesTextView.isEnabled = false
    }
}