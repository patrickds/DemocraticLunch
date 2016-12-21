package patrickds.github.democraticlunch.nearbyrestaurants

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.nearby_restaurants_list_item.view.*
import patrickds.github.democraticlunch.R
import patrickds.github.democraticlunch.extensions.ViewGroupExtensions.inflate
import patrickds.github.democraticlunch.nearbyrestaurants.domain.model.Restaurant

class NearbyRestaurantsAdapter(var _restaurants: List<Restaurant>) :
        RecyclerView.Adapter<NearbyRestaurantsAdapter.RestaurantViewHolder>() {

    override fun getItemCount() = _restaurants.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = parent.inflate(R.layout.nearby_restaurants_list_item)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = _restaurants[position]
        holder.bind(restaurant)
    }

    fun replaceData(restaurants: List<Restaurant>) {
        _restaurants = restaurants
        notifyDataSetChanged()
    }

    class RestaurantViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val nameTextView by lazy { view.restaurant_name }
        private val descriptionTextView by lazy { view.restaurant_description }
        private val imageImageView by lazy { view.restaurant_image }

        fun bind(restaurant: Restaurant) {
            nameTextView.text = restaurant.name
            descriptionTextView.text = "Description bla bla bla"
        }
    }
}