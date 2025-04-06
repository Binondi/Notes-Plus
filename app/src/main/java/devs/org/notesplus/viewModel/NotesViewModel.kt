package devs.org.notesplus.viewModel

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import devs.org.notesplus.models.Notes
import devs.org.notesplus.reposetory.NotesRepository

class NotesViewModel : ViewModel() {
    val repository = NotesRepository()
    private val _notes = mutableStateOf<List<Notes>>(emptyList())
    val notes :State<List<Notes>> = _notes
    private val _error = mutableStateOf<String?>(null)
    val error : State<String?> = _error

    init {
        fetchNotes()
    }

    private fun fetchNotes(){
        repository.fetchNotes(
            onSuccess = {notesList->
                _notes.value = notesList
            },
            onError = {errorMsg ->
                _error.value = errorMsg.message
            }
        )
    }

    fun addNote(note: Notes,onSuccess: ()-> Unit, onFailure:(Exception)-> Unit){
        repository.createNotes(note, onSuccess, onFailure)
    }

    fun editNote(note: Notes,onSuccess: ()-> Unit, onFailure:(Exception)-> Unit){
        repository.editNotes(note, onSuccess, onFailure)
    }

    fun deleteNote(key: String, onSuccess: () -> Unit, onError: (Exception) -> Unit){
        repository.deleteNote(key, onSuccess, onError)
    }
}