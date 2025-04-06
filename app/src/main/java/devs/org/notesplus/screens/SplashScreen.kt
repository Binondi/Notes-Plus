package devs.org.notesplus.screens

import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavHostController
import devs.org.notesplus.R
import devs.org.notesplus.routs.Routs

@Composable
@Preview
fun SplashScreen(navHostController: NavHostController) {
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.Center),
                painter = painterResource(R.drawable.logo),
                contentDescription = ""
            )
        }

        LaunchedEffect(Unit) {
            android.os.Handler(Looper.getMainLooper()).postDelayed({
                navHostController.navigate(Routs.notesScreen){
                    popUpTo(Routs.splashScreen){inclusive = true}
                }
            },2000)
        }
    }

}