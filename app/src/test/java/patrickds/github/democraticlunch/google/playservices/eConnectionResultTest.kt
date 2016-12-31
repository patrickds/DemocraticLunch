package patrickds.github.democraticlunch.google.playservices

import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test

class eConnectionResultTest {

    @Test
    fun succeeded_WhenSuccess_ReturnsTrue(){
        val connectionResult = eConnectionResult.SUCCESS
        assertTrue(connectionResult.succeeded())
    }

    @Test
    fun succeeded_WhenSuspended_ReturnsFalse(){
        val connectionResult = eConnectionResult.SUSPENDED
        assertFalse(connectionResult.succeeded())
    }
}