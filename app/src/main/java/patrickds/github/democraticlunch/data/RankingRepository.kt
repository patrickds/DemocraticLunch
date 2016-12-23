package patrickds.github.democraticlunch.data

import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Observable
import org.joda.time.LocalDate
import patrickds.github.democraticlunch.extensions.FirebaseExtensions.addSingleValueListener
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.restaurant_election.domain.model.RankingEntry
import patrickds.github.democraticlunch.restaurant_election.domain.model.RestaurantRanking
import patrickds.github.democraticlunch.restaurant_election.domain.repositories.IRankingRepository
import javax.inject.Inject

class RankingRepository
@Inject constructor() : IRankingRepository {

    val VOTING_KEY = "Voting"
    val ENTRIES_KEY = "Entries"
    val ELECTION_ENDED_KEY = "Ended"
    val WEEK_KEY = "WeekChoices"

    override fun update(restaurant: Restaurant) {

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
                .child(ELECTION_ENDED_KEY)
                .setValue(false)
    }

    override fun addWeekWinner(winnerId: String, weekOfYear: Int, weekDay: String) {
        FirebaseDatabase.getInstance()
                .reference
                .child(WEEK_KEY)
                .child(weekOfYear.toString())
                .child(weekDay)
                .setValue(winnerId)
    }

    override fun getElectionRankingByDay(day: Int): Observable<RestaurantRanking> {

        return Observable.create<RestaurantRanking> { emitter ->
            FirebaseDatabase.getInstance()
                    .reference
                    .child(VOTING_KEY)
                    .child(day.toString())
                    .addSingleValueListener({ snapshot ->

                        val rankingEntries = snapshot
                                .child(ENTRIES_KEY)
                                .children
                                .map { RankingEntry(it.key, it.value.toString().toInt()) }

                        emitter.onNext(RestaurantRanking(rankingEntries))
                        emitter.onComplete()

                    }, { error ->
                        emitter.onError(error.toException())
                    })
        }
    }

    override fun removeElectionByDay(yesterday: Int) {
        FirebaseDatabase.getInstance()
                .reference
                .child(VOTING_KEY)
                .child(yesterday.toString())
                .removeValue()
    }

    override fun endElection(today: Int) {

        FirebaseDatabase.getInstance()
                .reference
                .child(VOTING_KEY)
                .child(today.toString())
                .child(ELECTION_ENDED_KEY)
                .setValue(true)
    }

    override fun getLastElected(): Observable<String> {

        return Observable.create { emitter ->

            class Winner(val id: String, val weekYear: Int, val weekDay: String)

            FirebaseDatabase.getInstance()
                    .reference
                    .child(WEEK_KEY)
                    .addSingleValueListener({ snapshot ->

//                        if (snapshot.children.count() > 0) {

//                            snapshot.ref.orderByKey()
//                                    .limitToFirst(1)
//                                    .

//                            val winners = snapshot.children.map {
//                                val id = ""
//                                val weekYear = it.key.toInt()
//                                val weekDay = it.child()
//
//                                Winner(it.key.toInt())
//                            }
//                            emitter.onNext()
//                        }

                        emitter.onComplete()

                    }, { error ->

                    })
        }
    }
}