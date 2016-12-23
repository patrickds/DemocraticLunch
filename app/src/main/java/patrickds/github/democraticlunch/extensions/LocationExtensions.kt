package patrickds.github.democraticlunch.extensions

import android.location.Location

object LocationExtensions {

    fun Location.formatToApi() = "${latitude},${longitude}"
}