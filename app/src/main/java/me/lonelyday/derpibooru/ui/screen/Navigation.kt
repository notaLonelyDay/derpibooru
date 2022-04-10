package me.lonelyday.derpibooru.ui.screen

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.lonelyday.derpibooru.ui.screen.search.SearchScreen


@Composable
fun GlobalNavHost(
    navController: NavHostController,
    onNavDestChanged: (NavDest) -> Unit
) {
    var currentDest by remember { mutableStateOf(NavDest.SEARCH) }
    NavHost(navController = navController, startDestination = NavDest.startDest.navName) {
        composable(NavDest.SEARCH.navName) {
            currentDest = NavDest.SEARCH
            SearchScreen(navController = navController)
        }
    }

    LaunchedEffect(key1 = currentDest, block = {
        onNavDestChanged(currentDest)
    })
}

//todo migrate navName to strings.xml
enum class NavDest(
    val navName: String,
    val appBarName: String,
    val drawerName: String = appBarName,
    val isTopLevelDest: Boolean = false
) {

    SEARCH(
        navName = "search",
        appBarName = "Derpibooru",
        drawerName = "Search",
        isTopLevelDest = true
    ),
    SETTINGS(
        navName = "settings",
        appBarName = "Settings",
        drawerName = "Settings",
        isTopLevelDest = true
    );

    companion object {
        val startDest = SEARCH
    }
}