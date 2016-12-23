package patrickds.github.democraticlunch.restaurant_election.domain.repositories

import io.reactivex.Observable
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.VotingUpdate
import patrickds.github.democraticlunch.restaurant_election.domain.model.Voting

interface IVotingRepository {

    fun endVoting(today: Int)
    fun insertOrUpdate(restaurant: Restaurant)

    fun getVotingByDay(day: Int): Observable<Voting>
    fun removeVotingByDay(yesterday: Int)
    fun listenForVotingUpdates() : Observable<VotingUpdate>
}