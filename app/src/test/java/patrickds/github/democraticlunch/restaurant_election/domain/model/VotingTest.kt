package patrickds.github.democraticlunch.restaurant_election.domain.model


import org.junit.Assert.assertEquals
import org.junit.Test
import patrickds.github.democraticlunch.nearby_restaurants.domain.model.VoteEntry

class VotingTest {

    @Test
    fun electWinner_votingWith3EntriesShouldReturnWinner() {

        val entry1 = VoteEntry("1", 1)
        val entry2 = VoteEntry("2", 4)
        val entry3 = VoteEntry("3", 0)

        val entries = listOf(entry1, entry2, entry3)
        val hasEnded = true
        val voting = Voting(entries, hasEnded)

        val election = voting.electWinner()

        assertEquals(entry2, election.winner)
    }

    @Test(expected = VotingWithoutAnyEntryException::class)
    fun getWinner_votingWithNoEntriesShouldThrowException(){

        val hasEnded = true
        val voting = Voting(listOf(), hasEnded)

        voting.electWinner()
    }
}
