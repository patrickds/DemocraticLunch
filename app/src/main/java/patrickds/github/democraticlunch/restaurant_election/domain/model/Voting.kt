package patrickds.github.democraticlunch.restaurant_election.domain.model

import patrickds.github.democraticlunch.nearby_restaurants.domain.model.VoteEntry

class Voting(val entries: List<VoteEntry>, val hasEnded: Boolean) {

    fun hasEntries() = entries.isNotEmpty()

    fun getWinner(): VoteEntry {
        if (!hasEntries())
            throw Exception("Can't calculate the winner with a election with no entries")

        val winner = entries.maxBy { it.votes }

        return winner!!
    }
}