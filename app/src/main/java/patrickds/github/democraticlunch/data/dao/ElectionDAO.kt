package patrickds.github.democraticlunch.data.dao

class ElectionDAO() {

    constructor(id: String, date: String) : this() {
        this.id = id
        this.date = date
    }

    lateinit var id: String
    lateinit var date: String
}
