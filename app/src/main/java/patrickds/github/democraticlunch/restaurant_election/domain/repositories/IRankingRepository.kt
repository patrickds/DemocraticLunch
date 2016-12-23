package patrickds.github.democraticlunch.restaurant_election.domain.repositories

import io.reactivex.Observable
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.restaurant_election.domain.model.RestaurantRanking

interface IRankingRepository {

    fun addWeekWinner(winnerId: String, weekOfYear: Int, weekDay: String)
    fun endElection(today: Int)

    fun update(restaurant: Restaurant)
    fun getElectionRankingByDay(day: Int): Observable<RestaurantRanking>
    fun removeElectionByDay(yesterday: Int)
    fun getLastElected(): Observable<String>
}