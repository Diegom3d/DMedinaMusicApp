package com.example.dmedinamusicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dmedinamusicapp.navigation.Screen
import com.example.dmedinamusicapp.presentation.DetailScreen
import com.example.dmedinamusicapp.presentation.HomeScreen
import com.example.dmedinamusicapp.viewmodel.AlbumViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel: AlbumViewModel = viewModel()

            Surface(color = MaterialTheme.colorScheme.background) {


                NavHost(navController = navController, startDestination = Screen.Home.route) {
                    composable(Screen.Home.route) {
                        HomeScreen(navController, viewModel)
                    }
                    composable("detail/{albumId}") { backStackEntry ->
                        val albumId = backStackEntry.arguments?.getString("albumId") ?: ""
                        println("→ albumId recibido en DetailScreen: $albumId")

                        LaunchedEffect(albumId) {
                            viewModel.loadAlbumById(albumId)
                        }

                        DetailScreen(viewModel = viewModel)
                    }
                }
            }


        }
    }
}