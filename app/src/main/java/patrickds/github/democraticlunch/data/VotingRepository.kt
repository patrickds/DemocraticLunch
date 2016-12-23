package patrickds.github.democraticlunch.data

import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Observable
import org.joda.time.LocalDate
import patrickds.github.democraticlunch.extensions.FirebaseExtensions.addSingleValueListener
import patrickds.github.democraticlunch.extensions.FirebaseExtensions.addValueListener
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.VoteEntry
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.VotingUpdate
import patrickds.github.democraticlunch.restaurant_election.domain.model.Voting
import patrickds.github.democraticlunch.restaurant_election.domain.repositories.IVotingRepository
import javax.inject.Inject

class VotingRepository
@Inject constructor() : IVotingRepository {

    val VOTING_KEY = "Voting"
    val ENTRIES_KEY = "Entries"
    val VOTING_ENDED_KEY = "Ended"

    override fun insertOrUpdate(restaurant: Restaurant) {

        val reference = FirebaseDatabase
                .getInstance()
                .reference

        val now = LocalDate.now()

        reference.child(VOTING_KEY)
                .child(now.dayOfYear.toString())
                .child(ENTRIES_KEY)
                .child(restaurant.id)
                .setValue(restaurant.votes)

        reference.child(VOTING_KEY)
                .child(now.dayOfYear.toString())
                .child(VOTING_ENDED_KEY)
                .setValue(false)
    }

    override fun getVotingByDay(day: Int): Observable<Voting> {

        return Observable.create<Voting> { emitter ->
            FirebaseDatabase.getInstance()
                    .reference
                    .child(VOTING_KEY)
                    .child(day.toString())
                    .addSingleValueListener({ snapshot ->

                        if (snapshot.hasChildren()) {

                            val voteEntries = snapshot
                                    .child(ENTRIES_KEY)
                                    .children
                                    .map { VoteEntry(it.key, it.value.toString().toInt()) }

                            val ended = snapshot
                                    .child(VOTING_ENDED_KEY)
                                    .getValue(Boolean::class.java)

                            emitter.onNext(Voting(voteEntries, ended))
                        }

                        emitter.onComplete()
                    }, { error ->
                        emitter.onError(error.toException())
                    })
        }
    }

    override fun removeVotingByDay(yesterday: Int) {
        FirebaseDatabase.getInstance()
                .reference
                .child(VOTING_KEY)
                .child(yesterday.toString())
                .removeValue()
    }

    override fun endVoting(today: Int) {

        FirebaseDatabase.getInstance()
                .reference
                .child(VOTING_KEY)
                .child(today.toString())
                .child(VOTING_ENDED_KEY)
                .setValue(true)
    }

    override fun listenForVotingUpdates(): Observable<VotingUpdate> {

        val today = LocalDate.now().dayOfYear
        return Observable.create { emitter ->
            FirebaseDatabase.getInstance()
                    .reference
                    .child(VOTING_KEY)
                    .child(today.toString())
                    .child(ENTRIES_KEY)
                    .addValueListener({ snapshot ->

                        if (snapshot.hasChildren()) {
                            val entries = snapshot
                                    .children
                                    .map { VoteEntry(it.key, it.value.toString().toInt()) }

                            emitter.onNext(VotingUpdate(entries))
                        }
                    }, { error ->
                        emitter.onError(error.toException())
                    })
        }
    }
}