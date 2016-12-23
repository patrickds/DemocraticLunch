package patrickds.github.democraticlunch.restaurant_election.domain.usecase

import android.content.Context
import org.joda.time.LocalDate
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Election
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IElectionRepository
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IRestaurantRepository
import patrickds.github.democraticlunch.restaurant_election.NotificationUtils
import patrickds.github.democraticlunch.restaurant_election.domain.repositories.IRankingRepository
import javax.inject.Inject

class ChooseWinnerAndNotifyUser
@Inject constructor(
        private val _context: Context,
        private val _rankingRepository: IRankingRepository,
        private val _electionRepository: IElectionRepository,
        private val _restaurantRepository: IRestaurantRepository) {

    fun execute() {

        val now = LocalDate.now()
        val today = now.dayOfYear
        val yesterday = now.minusDays(1).dayOfYear

        _rankingRepository.getElectionRankingByDay(today)
                .subscribe { ranking ->
                    if (ranking.hasEntries()) {

                        val winner = ranking.getWinner()

                        val election = Election(now, winner.id)
                        _electionRepository.insertOrUpdate(election)

//                        val dayOfWeek = now.dayOfWeek().getAsText(Locale.ENGLISH)

//                        _rankingRepository.addWeekWinner(winner.id, now.weekOfWeekyear, dayOfWeek)
//                        _rankingRepository.endElection(today)
//                        _rankingRepository.removeElectionByDay(yesterday)
                        _restaurantRepository.clearVoteCache()

                        NotificationUtils.remindUser(_context, "HEY I'M A WINNER YEY")
                    }
                }
    }
}