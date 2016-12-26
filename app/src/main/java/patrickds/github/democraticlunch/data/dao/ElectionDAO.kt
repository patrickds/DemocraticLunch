package patrickds.github.democraticlunch.data.dao

import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.Election

class ElectionDAO() {

    constructor(date: String, entry: VoteEntryDAO) : this() {
        this.date = date
        this.entry = entry
    }

    lateinit var date: String
    lateinit var entry: VoteEntryDAO

    fun toDomain(): Election {
        val date = LocalDate.parse(date, DateTimeFormat.mediumDate())
        return Election(date, entry.toVoteEntry())
    }

    companion object {
        fun fromDomain(election: Election): ElectionDAO {
            val entry = VoteEntryDAO.fromDomain(election.winner)
            val date = election.date.toString(DateTimeFormat.mediumDate())
            return ElectionDAO(date, entry)
        }
    }
}
