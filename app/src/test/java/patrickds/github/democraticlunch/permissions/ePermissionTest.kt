package patrickds.github.democraticlunch.permissions

import org.junit.Assert.assertEquals
import org.junit.Test

class ePermissionTest {

    @Test
    fun toAndroidPermission_WhenLocationPermission_ShouldBeEqualToAndroidLocationGroupPermission(){
        val permission = ePermission.LOCATION

        val androidPermission = permission.toAndroidPermission()

        assertEquals(android.Manifest.permission.ACCESS_FINE_LOCATION, androidPermission)
    }
}