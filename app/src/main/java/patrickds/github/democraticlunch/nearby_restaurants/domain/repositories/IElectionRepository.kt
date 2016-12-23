package patrickds.github.democraticlunch.nearby_restaurants.domain.repositories

import io.reactivex.Observable
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Election

interface IElectionRepository {

    fun insertOrUpdate(election: Election)
    fun getLastElection(): Observable<Election>
    fun getWeekElections(): Observable<List<Election>>
}