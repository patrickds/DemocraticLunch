package patrickds.github.democraticlunch.data

import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Observable
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import patrickds.github.democraticlunch.data.dao.ElectionDAO
import patrickds.github.democraticlunch.extensions.FirebaseExtensions.addSingleValueListener
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Election
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IElectionRepository
import javax.inject.Inject

class ElectionRepository
@Inject constructor() : IElectionRepository {

    private val ELECTIONS_KEY = "Elections"

    override fun insertOrUpdate(election: Election) {

        val electionDao = ElectionDAO.fromDomain(election)
        val jsonDate = election.date.toString(DateTimeFormat.forPattern("YYYY-MM-dd"))

        FirebaseDatabase.getInstance()
                .reference
                .child(ELECTIONS_KEY)
                .child(jsonDate)
                .setValue(electionDao)
    }

    override fun getLastElection(): Observable<Election> {
        return Observable.create<Election> { emitter ->

            FirebaseDatabase.getInstance()
                    .reference
                    .child(ELECTIONS_KEY)
                    .addSingleValueListener({ snapshot ->

                        if (snapshot.hasChildren() && snapshot.childrenCount > 0) {
                            snapshot.children.maxBy { it.key }?.let {
                                val election = it.getValue(ElectionDAO::class.java)
                                emitter.onNext(election.toDomain())
                            }
                        }

                        emitter.onComplete()
                    }, { error ->
                        emitter.onError(error.toException())
                    })
        }
    }

    override fun getWeekElections(): Observable<List<Election>> {

        return Observable.create<List<Election>> { emitter ->

            FirebaseDatabase.getInstance()
                    .reference
                    .child(ELECTIONS_KEY)
                    .addSingleValueListener({ snapshot ->

                        if (snapshot.hasChildren() && snapshot.childrenCount > 0) {

                            val now = LocalDate.now()
                            val firstDayOfWeek = now.minusDays(now.dayOfWeek)

                            val elections = snapshot.children.map {
                                val election = it.getValue(ElectionDAO::class.java)
                                election.toDomain()
                            }.filter {
                                it.date > firstDayOfWeek
                            }

                            emitter.onNext(elections)
                        }

                        emitter.onComplete()
                    }, { error ->
                        emitter.onError(error.toException())
                    })
        }
    }
}