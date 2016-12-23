package patrickds.github.democraticlunch.restaurant_election.domain.usecase

import android.content.Context
import org.joda.time.LocalDate
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IRestaurantRepository
import patrickds.github.democraticlunch.restaurant_election.NotificationUtils
import patrickds.github.democraticlunch.restaurant_election.domain.repositories.IRankingRepository
import java.util.*
import javax.inject.Inject

class ChooseWinnerAndNotifyUser
@Inject constructor(
        private val _context: Context,
        private val _rankingRepository: IRankingRepository,
        private val _restaurantRepository: IRestaurantRepository) {

    fun execute() {
        _rankingRepository.getCurrentElectionRanking()
                .subscribe { ranking ->
                    if (ranking.hasEntries()) {

                        val winner = ranking.getWinner()

                        val now = LocalDate.now()
                        val dayOfWeek = now.dayOfWeek().getAsText(Locale.ENGLISH)

                        _rankingRepository.addWeekWinner(winner.id, now.weekOfWeekyear, dayOfWeek)
                        _rankingRepository.removeCurrentElectionRanking()
                        _restaurantRepository.clearVoteCache()

                        NotificationUtils.remindUser(_context, "HEY I'M A WINNER YEY")
                    }
                }
    }
}