package patrickds.github.democraticlunch.nearby_restaurants

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.nearby_restaurants_list_item.view.*
import patrickds.github.democraticlunch.R
import patrickds.github.democraticlunch.data.FirebaseVoteRepository
import patrickds.github.democraticlunch.data.RealmRestaurantRepository
import patrickds.github.democraticlunch.extensions.ViewGroupExtensions.inflate
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.usecase.VoteOnRestaurant

class NearbyRestaurantsAdapter(var _restaurants: MutableList<Restaurant>) :
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

    fun replaceData(restaurants: MutableList<Restaurant>) {
        _restaurants = restaurants
        notifyDataSetChanged()
    }

    class RestaurantViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val nameTextView by lazy { view.restaurant_name }
        private val votesTextView by lazy { view.restaurant_votes }
        private val imageImageView by lazy { view.restaurant_image }
        private val voteButton by lazy { view.vote_button }

        fun bind(restaurant: Restaurant) {
            nameTextView.text = restaurant.name
            votesTextView.text = "Votes: ${restaurant.votes}"
            updateButtonText(restaurant)

            val restaurantRepository = RealmRestaurantRepository()
            val voteRepository = FirebaseVoteRepository()
            voteButton.setOnClickListener {
                VoteOnRestaurant(voteRepository, restaurantRepository)
                        .execute(VoteOnRestaurant.RequestValues(restaurant))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ next ->

                        }, { error ->

                        }, {
                            updateButtonText(restaurant)
                        })
            }
        }

        fun updateButtonText(restaurant: Restaurant) {
            if (restaurant.isVoted)
                voteButton.text = "UNVOTE"
            else
                voteButton.text = "VOTE"
        }
    }

    fun add(restaurant: Restaurant) {
        _restaurants.add(restaurant)

        FirebaseDatabase.getInstance()
                .reference
                .child("CurrentVoting")
                .child(restaurant.id)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError?) {
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {

                        snapshot.value?.let {
                            val votes = it.toString().toInt()
                            restaurant.votes = votes;
                            notifyDataSetChanged()
                        }
                    }
                })

        notifyDataSetChanged()
    }

    fun clear() {
        _restaurants.clear()
        notifyDataSetChanged()
    }
}