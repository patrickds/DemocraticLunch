package patrickds.github.democraticlunch.nearby_restaurants.domain.model

import patrickds.github.democraticlunch.BuildConfig

class Restaurant(
        var id: String,
        var name: String,
        var address: String,
        var photoReference: String,
        var rating: Float,
        var votes: Int = 0,
        var isVoted: Boolean = false,
        var wasSelectedThisWeek: Boolean = false) {

    fun vote() {
        if (isVoted) {
            votes--
            isVoted = false
        } else {
            votes++
            isVoted = true
        }
    }

    fun buildPhotoUrl(): String {
        val key = BuildConfig.GOOGLE_WEB_SERVICE_KEY
        return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=500&photoreference=$photoReference&key=$key"
    }
}
