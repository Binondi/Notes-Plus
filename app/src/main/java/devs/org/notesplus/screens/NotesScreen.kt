package devs.org.notesplus.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import devs.org.notesplus.R
import devs.org.notesplus.models.Notes
import devs.org.notesplus.routs.Routs
import devs.org.notesplus.viewModel.NotesViewModel

@Composable
fun NotesScreen(
    navHostController: NavHostController,
    viewModel: NotesViewModel = viewModel()
) {

    val notes by viewModel.notes
    val error by viewModel.error
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navHostController.navigate("${Routs.editNoteScreen}///")
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        }
    ) { innerPadding ->
        if (notes.isNotEmpty() || error != null) {
            isLoading = false
        }
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(40.dp)
                )
            } else {
                if (error != null) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "Something went wrong $error"
                    )
                } else if (notes.isEmpty()) {
                    Text(modifier = Modifier.align(Alignment.Center), text = "No Notes Available")

                } else {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = context.getString(R.string.app_name),
                            modifier = Modifier.padding(16.dp),
                            style = TextStyle(
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        LazyVerticalStaggeredGrid(
                            columns = StaggeredGridCells.Fixed(2),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 15.dp)
                                .padding(top = 15.dp),
                            verticalItemSpacing = 8.dp,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(notes) {
                                LazyItems(it, navHostController = navHostController)
                            }
                            item {
                                Spacer(
                                    modifier = Modifier
                                        .height(10.dp)
                                        .fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}


@Composable
fun LazyItems(item: Notes,viewModel: NotesViewModel = viewModel(), navHostController: NavHostController) {
    var isMenuOpen by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Card(modifier = Modifier.clickable {
        navHostController.navigate("${Routs.editNoteScreen}/${item.title}/${item.key}/${item.content}")
    }
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            DropdownMenu(
                offset = DpOffset(x = (40).dp, y = (-70).dp),
                expanded = isMenuOpen,
                modifier = Modifier.padding(8.dp),
                onDismissRequest = { isMenuOpen = false },
                content = {
                    DropdownMenuItem(
                        text = { Text("Edit") },
                        onClick = {
                            isMenuOpen = false
                            navHostController.navigate("${Routs.editNoteScreen}/${item.title}/${item.key}/${item.content}")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Delete") },
                        onClick = {
                            isMenuOpen = false
                            viewModel.deleteNote(item.key, onSuccess = {
                            },
                                onError = {
                                    Toast.makeText(
                                        context,
                                        "Failed to delete this note",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                })
                        }
                    )
                })
            Image(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 6.dp, end = 6.dp)
                    .size(20.dp)
                    .clickable {
                        isMenuOpen = true
                    },
                imageVector = Icons.Default.MoreVert, contentDescription = "",
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.White),
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    modifier = Modifier.padding(
                        top = 6.dp,
                        bottom = 2.dp,
                        start = 4.dp,
                        end = 30.dp
                    ),
                    text = item.title,
                    style = TextStyle(
                        fontSize = 23.sp
                    )

                )
                Text(
                    modifier = Modifier.padding(
                        top = 2.dp,
                        bottom = 6.dp,
                        start = 4.dp,
                        end = 4.dp
                    ),
                    text = item.content,
                    style = TextStyle(
                        fontSize = 14.sp
                    )
                )
            }
        }
    }


}