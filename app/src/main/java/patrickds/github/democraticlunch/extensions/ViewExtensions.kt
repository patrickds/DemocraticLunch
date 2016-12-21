package patrickds.github.democraticlunch.extensions

import android.support.design.widget.Snackbar
import android.view.View

object ViewExtensions {
    fun View.showLongSnackBar(message: String) =
            Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()

    fun View.showShortSnackBar(message: String) =
            Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

