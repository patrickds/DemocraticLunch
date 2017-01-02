package patrickds.github.democraticlunch.google.places

import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Restaurant
import patrickds.github.democraticlunch.nearby_restaurants.domain.repositories.IVotedRestaurantsDataSource

class GooglePlaceDAO() {

    var place_id: String? = null
    var name: String? = null
    var vicinity: String? = null
    var rating: Float? = 0f
    var photos: List<PlacePhotoDAO>? = null

    constructor(
            placeId: String,
            name: String,
            vicinity: String,
            rating: Float,
            photos: List<PlacePhotoDAO>) : this() {

        this.place_id = placeId
        this.name = name
        this.vicinity = vicinity
        this.rating = rating
        this.photos = photos
    }

    fun toDomain(_votedRestaurantsCache: IVotedRestaurantsDataSource): Restaurant {
        var reference = ""
        if (photos != null && photos!!.count() > 0)
            reference = photos!!.first().photo_reference!!

        val votes = 0
        val isVoted = _votedRestaurantsCache.getIsVoted(place_id!!)

        return Restaurant(
                place_id!!,
                name!!,
                vicinity!!,
                reference,
                rating!!,
                votes,
                isVoted)
    }

}