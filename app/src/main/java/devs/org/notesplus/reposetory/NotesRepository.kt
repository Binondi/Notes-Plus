package devs.org.notesplus.reposetory

import android.provider.ContactsContract
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import devs.org.notesplus.models.Notes

class NotesRepository {
    val database = FirebaseDatabase.getInstance()
    val notesRef = database.reference.child("notes")

    fun fetchNotes(onSuccess: (List<Notes>) -> Unit, onError: (Exception) -> Unit) {
        notesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var notes = mutableListOf<Notes>()
                for (child in snapshot.children) {
                    val note = child.getValue(Notes::class.java)
                    note?.let { notes.add(it) }
                }
                onSuccess(notes)
            }

            override fun onCancelled(error: DatabaseError) {
                onError(error.toException())
            }
        })

    }

    fun createNotes(note: Notes, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        val pushKey = notesRef.push().key.toString()
        note.key = pushKey
        notesRef.child(pushKey).setValue(note)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it) }

    }

    fun editNotes(note: Notes, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        notesRef.child(note.key).setValue(note)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it) }

    }

    fun deleteNote(key: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        notesRef.child(key).removeValue()
            .addOnSuccessListener {
            onSuccess()
        }
            .addOnFailureListener{
                onError(it)
            }
    }
}