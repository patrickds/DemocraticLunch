package patrickds.github.democraticlunch.restaurant_election.domain.repositories

import io.reactivex.Observable
import patrickds.github.democraticlunch.restaurant_election.domain.model.RestaurantRanking

interface IRankingRepository {

    fun addWeekWinner(winnerId: String, weekOfYear: Int, weekDay: String)
    fun getCurrentElectionRanking(): Observable<RestaurantRanking>
    fun removeCurrentElectionRanking()
}