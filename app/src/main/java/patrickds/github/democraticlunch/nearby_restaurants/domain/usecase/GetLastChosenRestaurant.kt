package patrickds.github.democraticlunch.nearby_restaurants.domain.usecase

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IElectionRepository
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IRestaurantRepository
import javax.inject.Inject

open class GetLastChosenRestaurant
@Inject constructor(
        private val _electionRepository: IElectionRepository,
        private val _restaurantRepository: IRestaurantRepository) {

    fun execute(): Observable<Restaurant> {

        return _electionRepository.getLastElection()
                .observeOn(Schedulers.io())
                .flatMap {
                    _restaurantRepository.getById(it.restaurantId)
                }
    }
}