package devs.org.notesplus

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import devs.org.notesplus.routs.Routs
import devs.org.notesplus.screens.EditNoteScreen
import devs.org.notesplus.screens.NotesScreen
import devs.org.notesplus.screens.SplashScreen
import devs.org.notesplus.ui.theme.NotesPlusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesPlusTheme {
                val navHostController = rememberNavController()
                NavHost(navController = navHostController, startDestination = Routs.splashScreen) {
                    composable(route = Routs.splashScreen) { SplashScreen(navHostController = navHostController) }
                    composable(route = Routs.notesScreen) { NotesScreen(navHostController = navHostController) }
                    composable(
                        route = "${Routs.editNoteScreen}/{title}/{key}/{content}",
                        arguments = listOf(
                            navArgument("title") { type = NavType.StringType },
                            navArgument("key") { type = NavType.StringType },
                            navArgument("content") { type = NavType.StringType },
                        )
                    ) {backStackEntry ->
                        val title = Uri.decode(backStackEntry.arguments?.getString("title").orEmpty())
                        val key = Uri.decode(backStackEntry.arguments?.getString("key").orEmpty())
                        val content = Uri.decode(backStackEntry.arguments?.getString("content").orEmpty())
                        EditNoteScreen(navHostController = navHostController, title = title, keyValue = key, content = content)
                    }
                }
            }
        }
    }
}