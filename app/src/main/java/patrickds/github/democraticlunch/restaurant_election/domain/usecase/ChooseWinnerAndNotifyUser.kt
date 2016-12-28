package patrickds.github.democraticlunch.restaurant_election.domain.usecase

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.joda.time.LocalDate
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Election
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IElectionRepository
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IRestaurantRepository
import patrickds.github.democraticlunch.restaurant_election.RestaurantElectedNotification
import patrickds.github.democraticlunch.restaurant_election.domain.repositories.IVotingRepository
import timber.log.Timber
import javax.inject.Inject

open class ChooseWinnerAndNotifyUser
@Inject constructor(
        private val _votingRepository: IVotingRepository,
        private val _electionRepository: IElectionRepository,
        private val _restaurantRepository: IRestaurantRepository,
        private val _notification: RestaurantElectedNotification) {

    fun execute() {

        val now = LocalDate.now()
        val today = now.dayOfYear

        _votingRepository.getVotingByDay(today)
                .subscribe { voting ->
                    if (voting.hasEntries()) {

                        val election = voting.electWinner()

                        _electionRepository.insertOrUpdate(election)
                        _votingRepository.endVoting(today)
                        _restaurantRepository.clearVoteCache()

                        fireNotification(election)
                    }
                }
    }

    private fun fireNotification(election: Election) {

        val restaurantId = election.winner.restaurantId
        _restaurantRepository.getById(restaurantId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ restaurant ->
                    _notification.notify(restaurant)
                }, { error ->
                    Timber.e(error)
                })
    }
}