package me.lonelyday.derpibooru.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.lonelyday.derpibooru.ui.screen.search.SearchScreen


@Composable
fun NavigationScreen(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "search"){
        composable("search") { SearchScreen(navController = navController) }
    }
}