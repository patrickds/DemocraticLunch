package patrickds.github.democraticlunch.nearby_restaurants.domain.model

import org.joda.time.LocalDate

data class Election(val date: LocalDate, val winner: VoteEntry)
