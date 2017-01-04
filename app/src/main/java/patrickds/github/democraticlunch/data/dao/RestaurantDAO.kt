package patrickds.github.democraticlunch.data.dao

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RestaurantDAO() : RealmObject() {

    @PrimaryKey
    lateinit var id: String
    var isVoted: Boolean = false
    var votes: Int = 0

    constructor(id: String, isVoted: Boolean, votes: Int) : this() {
        this.id = id
        this.isVoted = isVoted
        this.votes = votes
    }
}