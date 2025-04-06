package devs.org.notesplus.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import devs.org.notesplus.models.Notes
import devs.org.notesplus.viewModel.NotesViewModel

@Composable
fun EditNoteScreen(
    viewModel: NotesViewModel = viewModel(),
    navHostController: NavHostController,
    title: String = "",
    content: String = "",
    keyValue: String = "",

) {

    var text by remember { mutableStateOf(title) }
    var desc by remember { mutableStateOf(content) }
    val key by remember { mutableStateOf(keyValue) }
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    var isEnabled by remember { mutableStateOf(true) }

    Scaffold(
        floatingActionButton = {

            FloatingActionButton(onClick = {
                if (isEnabled){
                    isEnabled = false
                    val note = if (key.isNotEmpty()) Notes(key = key, title = text, content = desc) else Notes(title = text, content = desc)

                    if (key.isNotEmpty()){
                        viewModel.editNote(note,onSuccess = {
                            navHostController.popBackStack()
                        },
                            onFailure = {
                                isEnabled = true
                                Toast.makeText(
                                    context,
                                    "Error editing note : ${it.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            })
                    }else {
                        viewModel.addNote(
                            note = note, onSuccess = {
                                navHostController.popBackStack()
                            },
                            onFailure = {
                                isEnabled = true
                                Toast.makeText(
                                    context,
                                    "Error saving note : ${it.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            })
                    }
                }


            }) {
                Icon(imageVector = Icons.Default.Done, contentDescription = null)
            }
        }
    ) {innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Edit Notes",
                    modifier = Modifier.padding(vertical = 15.dp, horizontal = 10.dp),
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                TextField(
                    value = text,
                    onValueChange = { newText ->
                        text = newText
                    },
                    placeholder = { Text("Enter title") },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                )
                TextField(
                    value = desc,
                    onValueChange = { newText2 ->
                        desc = newText2
                    },
                    placeholder = { Text("Enter something") },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                )
            }
        }
    }
}