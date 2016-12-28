package patrickds.github.democraticlunch.nearby_restaurants.domain.usecase

import io.reactivex.Observable
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.VotingUpdate
import patrickds.github.democraticlunch.restaurant_election.domain.repositories.IVotingRepository
import javax.inject.Inject

open class ListenForVotingUpdates
@Inject constructor(private val _votingRepository: IVotingRepository) {

    open fun execute(): Observable<VotingUpdate> {
        return _votingRepository.listenForVotingUpdates()
    }
}