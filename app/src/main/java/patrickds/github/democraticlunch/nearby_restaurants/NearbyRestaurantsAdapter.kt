package patrickds.github.democraticlunch.nearby_restaurants

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import patrickds.github.democraticlunch.R
import patrickds.github.democraticlunch.extensions.ViewGroupExtensions.inflate
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant

class NearbyRestaurantsAdapter(var _restaurants: MutableList<Restaurant>) :
        RecyclerView.Adapter<RestaurantItemViewHolder>() {

    override fun getItemCount() = _restaurants.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantItemViewHolder {
        val view = parent.inflate(R.layout.nearby_restaurants_list_item)
        return RestaurantItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantItemViewHolder, position: Int) {
        val restaurant = _restaurants[position]
        holder.bind(restaurant)
    }

    fun replaceData(restaurants: MutableList<Restaurant>) {
        _restaurants = restaurants
        notifyDataSetChanged()
    }

    fun add(restaurant: Restaurant) {
        _restaurants.add(restaurant)

//        FirebaseDatabase.getInstance()
//                .reference
//                .child("CurrentVoting")
//                .child(restaurant.id)
//                .addValueEventListener(object : ValueEventListener {
//                    override fun onCancelled(p0: DatabaseError?) {
//                    }
//
//                    override fun onDataChange(snapshot: DataSnapshot) {
//
//                        snapshot.value?.let {
//                            val votes = it.toString().toInt()
//                            restaurant.votes = votes;
//                            notifyDataSetChanged()
//                        }
//                    }
//                })

        notifyDataSetChanged()
    }

    fun clear() {
        _restaurants.clear()
        notifyDataSetChanged()
    }
}
