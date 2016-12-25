package patrickds.github.democraticlunch.nearby_restaurants.domain.usecase

import io.reactivex.Observable
import org.joda.time.LocalDate
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.eVotingStatus
import patrickds.github.democraticlunch.restaurant_election.domain.repositories.IVotingRepository
import javax.inject.Inject

open class VerifyVotingStatus
@Inject constructor(private val _votingRepository: IVotingRepository) {

    fun execute(): Observable<eVotingStatus> {

        val today = LocalDate.now()
        return _votingRepository.getVotingByDay(today.dayOfYear)
                .map { voting ->
                    if (voting.hasEnded)
                        eVotingStatus.ENDED
                    else
                        eVotingStatus.ONGOING
                }
    }
}