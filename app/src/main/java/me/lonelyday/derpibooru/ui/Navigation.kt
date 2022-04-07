package me.lonelyday.derpibooru.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.lonelyday.derpibooru.ui.screen.search.SearchScreen


@Composable
fun GlobalNavigationScreen(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavDest.SEARCH.n){
        composable(NavDest.SEARCH.n) { SearchScreen(navController = navController) }
    }
}

// todo: remove n
enum class NavDest(val n: String) {
    SEARCH("search")
}