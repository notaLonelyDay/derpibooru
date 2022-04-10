package me.lonelyday.derpibooru.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import me.lonelyday.derpibooru.db.vo.Image
import me.lonelyday.derpibooru.ui.NavDest

@Composable
fun NavDrawer(
    navController: NavHostController,
    currentNavDest: NavDest,
    featuredImage: Image?
) {
    Column {
        featuredImage?.let { FeaturedImage(image = it) }
        DrawerItems(currentNavDest = currentNavDest, onItemClicked = { navController.navigate(it.appBarName) })
    }
}

@Composable
fun FeaturedImage(image: Image) {

}

@Composable
fun DrawerItems(
    currentNavDest: NavDest,
    onItemClicked: (NavDest) -> Unit
) {
    val items = remember { NavDest.values().filter { it.isTopLevelDest }.toList() }
    Column {
        items.forEach { navDest ->
            Text(text = navDest.drawerName)
        }
    }
}