package patrickds.github.democraticlunch.data

import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Observable
import patrickds.github.democraticlunch.extensions.FirebaseExtensions.addSingleValueListener
import patrickds.github.democraticlunch.restaurant_election.domain.model.RankingEntry
import patrickds.github.democraticlunch.restaurant_election.domain.model.RestaurantRanking
import patrickds.github.democraticlunch.restaurant_election.domain.repositories.IRankingRepository
import javax.inject.Inject

class RankingRepository
@Inject constructor() : IRankingRepository {

    override fun addWeekWinner(winnerId: String, weekOfYear: Int, weekDay: String) {
        FirebaseDatabase.getInstance().reference.child("Week")
                .child(weekOfYear.toString())
                .child(weekDay)
                .setValue(winnerId)
    }

    override fun getCurrentElectionRanking(): Observable<RestaurantRanking> {

        return Observable.create<RestaurantRanking> { emitter ->
            FirebaseDatabase.getInstance()
                    .reference
                    .child("CurrentVoting")
                    .addSingleValueListener({ snapshot ->
                        val entries = snapshot
                                .children
                                .map { RankingEntry(it.key, it.value.toString().toInt()) }

                        emitter.onNext(RestaurantRanking(entries))
                        emitter.onComplete()

                    }, { error ->
                        emitter.onError(error.toException())
                    })
        }
    }

    override fun removeCurrentElectionRanking() {
        FirebaseDatabase.getInstance()
                .reference
                .child("CurrentVoting")
                .removeValue()
    }
}