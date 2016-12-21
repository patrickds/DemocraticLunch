package patrickds.github.democraticlunch.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

object ViewGroupExtensions {

    fun ViewGroup.inflate(layout: Int, attachToParent: Boolean = false): View =
            LayoutInflater.from(context).inflate(layout, this, attachToParent)
}