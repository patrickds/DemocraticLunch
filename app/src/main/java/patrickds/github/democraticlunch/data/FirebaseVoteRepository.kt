package patrickds.github.democraticlunch.data

import com.google.firebase.database.FirebaseDatabase
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IVoteRepository
import javax.inject.Inject

class FirebaseVoteRepository @Inject constructor() : IVoteRepository {

    override fun insertOrUpdate(restaurant: Restaurant) {
        val database = FirebaseDatabase.getInstance()
        val reference = database.reference

        reference.child("CurrentVoting")
                .child(restaurant.id)
                .setValue(restaurant.votes)
    }
}