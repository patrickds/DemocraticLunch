package patrickds.github.democraticlunch.data.entities

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RestaurantRealm() : RealmObject() {

    @PrimaryKey
    lateinit var id: String
    var isVoted: Boolean = false

    constructor(id: String, isVoted: Boolean) : this() {
        this.id = id;
        this.isVoted = isVoted
    }
}