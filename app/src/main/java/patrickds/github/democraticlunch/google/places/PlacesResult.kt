package patrickds.github.democraticlunch.google.places

class PlacesResult() {

    var status: String? = null
    var results: List<GooglePlaceDAO>? = null

    constructor(status: String, results: List<GooglePlaceDAO>) : this() {
        this.status = status
        this.results = results
    }
}

