package patrickds.github.democraticlunch.restaurant_election.domain.model

class RestaurantRanking(val entries: List<RankingEntry>) {

    fun hasEntries() = entries.isNotEmpty()

    fun getWinner(): RankingEntry {
        if(!hasEntries())
            throw Exception("Can't calculate the winner with a election with no entries")

        val winner = entries.maxBy { it.votes }

        return winner!!
    }
}