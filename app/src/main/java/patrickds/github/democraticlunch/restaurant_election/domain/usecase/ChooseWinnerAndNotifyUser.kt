package patrickds.github.democraticlunch.restaurant_election.domain.usecase

import org.joda.time.LocalDate
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Election
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IElectionRepository
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IRestaurantRepository
import patrickds.github.democraticlunch.restaurant_election.RestaurantElectedNotification
import patrickds.github.democraticlunch.restaurant_election.domain.repositories.IVotingRepository
import javax.inject.Inject

class ChooseWinnerAndNotifyUser
@Inject constructor(
        private val _rankingRepository: IVotingRepository,
        private val _electionRepository: IElectionRepository,
        private val _restaurantRepository: IRestaurantRepository,
        private val _notification: RestaurantElectedNotification) {

    fun execute() {

        val now = LocalDate.now()
        val today = now.dayOfYear

        _rankingRepository.getVotingByDay(today)
                .subscribe { voting ->
                    if (voting.hasEntries()) {

                        val winner = voting.getWinner()

                        val election = Election(now, winner.restaurantId)
                        _electionRepository.insertOrUpdate(election)

                        _rankingRepository.endVoting(today)
                        _restaurantRepository.clearVoteCache()

                        _notification.show(winner.restaurantId)
                    }
                }
    }
}