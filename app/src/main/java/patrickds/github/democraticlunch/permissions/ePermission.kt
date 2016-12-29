package patrickds.github.democraticlunch.permissions


enum class ePermission {
    LOCATION;

    fun toAndroidPermission(): String {
        return android.Manifest.permission.ACCESS_FINE_LOCATION
    }
}