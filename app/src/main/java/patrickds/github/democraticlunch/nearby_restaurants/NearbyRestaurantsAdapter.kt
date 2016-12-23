package patrickds.github.democraticlunch.nearby_restaurants

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import patrickds.github.democraticlunch.R
import patrickds.github.democraticlunch.extensions.ViewGroupExtensions.inflate
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.VotingUpdate

class NearbyRestaurantsAdapter(var _restaurants: MutableList<Restaurant>) :
        RecyclerView.Adapter<RestaurantItemViewHolder>() {

    private var _isEnable = true

    override fun getItemCount() = _restaurants.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantItemViewHolder {
        val view = parent.inflate(R.layout.nearby_restaurants_list_item)
        return RestaurantItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantItemViewHolder, position: Int) {
        val restaurant = _restaurants[position]
        holder.bind(restaurant)

        if (!_isEnable)
            holder.disable()
    }

    fun replaceData(restaurants: MutableList<Restaurant>) {
        _restaurants = restaurants
        notifyDataSetChanged()
    }

    fun add(restaurant: Restaurant) {
        _restaurants.add(restaurant)
        notifyDataSetChanged()
    }

    fun clear() {
        _restaurants.clear()
        notifyDataSetChanged()
    }

    fun disable() {
        _isEnable = false
        notifyDataSetChanged()
    }

    fun updateVotes(votingUpdate: VotingUpdate) {
        votingUpdate.entries.forEach { voteUpdate ->
            _restaurants.find { it.id == voteUpdate.restaurantId }?.let {

                it.votes = voteUpdate.votes
                val index = _restaurants.indexOf(it)
                notifyItemChanged(index)
            }
        }
    }
}
