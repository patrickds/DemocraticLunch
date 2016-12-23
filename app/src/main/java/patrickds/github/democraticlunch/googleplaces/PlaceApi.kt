package patrickds.github.democraticlunch.googleplaces

class PlaceApi {

    var id: String? = null
    var name: String? = null
    var reference: String? = null

    override fun toString(): String {
        return "$name - $id - $reference"
    }
}