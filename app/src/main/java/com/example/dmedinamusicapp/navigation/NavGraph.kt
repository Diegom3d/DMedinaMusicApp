package com.example.dmedinamusicapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.dmedinamusicapp.presentation.DetailScreen
import com.example.dmedinamusicapp.presentation.HomeScreen
import com.example.dmedinamusicapp.viewmodel.AlbumViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{albumId}") {
        fun createRoute(albumId: String) = "detail/$albumId"
    }
}

@Composable
fun NavGraph(navController: NavHostController, viewModel: AlbumViewModel) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("albumId") { type = NavType.StringType })
        ) { backStackEntry ->
            val albumId = backStackEntry.arguments?.getString("albumId") ?: return@composable

            LaunchedEffect(albumId) {
                viewModel.loadAlbumById(albumId)
            }


        }
    }
}