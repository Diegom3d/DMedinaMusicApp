package com.example.dmedinamusicapp.navigation


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{albumId}") {
        fun createRoute(albumId: String) = "detail/$albumId"
    }
}