package patrickds.github.democraticlunch.extensions

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

object FirebaseExtensions {

    fun DatabaseReference.addSingleValueListener(
            onDataChanged: (snapshot: DataSnapshot) -> Unit,
            onCancelled: (error: DatabaseError) -> Unit) {

        addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                onCancelled(error)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                onDataChanged(snapshot)
            }
        })
    }
}