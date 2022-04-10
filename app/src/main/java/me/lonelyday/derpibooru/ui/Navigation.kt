package me.lonelyday.derpibooru.ui

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
    NavHost(navController = navController, startDestination = NavDest.startDest.localName) {
        composable(NavDest.SEARCH.localName) {
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
    val localName: String,
    val appBarName: String,
    val drawerName: String = appBarName,
    val isTopLevelDest: Boolean = false
) {

    SEARCH(
        localName = "search",
        appBarName = "Derpibooru",
        drawerName = "Search",
        isTopLevelDest = true
    );

    companion object {
        val startDest = SEARCH
    }
}