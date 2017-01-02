package patrickds.github.democraticlunch.google.places

class PlacePhotoDAO() {

    var height: Int = 0
    var width: Int = 0
    var photo_reference: String? = null

    constructor(height: Int, width: Int, reference: String) : this() {
        this.height = height
        this.width = width
        this.photo_reference = reference
    }
}