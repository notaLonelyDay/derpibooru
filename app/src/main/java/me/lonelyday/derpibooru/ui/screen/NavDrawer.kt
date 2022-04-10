package me.lonelyday.derpibooru.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.skydoves.landscapist.glide.GlideImage
import me.lonelyday.derpibooru.db.vo.Image

@Composable
fun NavDrawer(
    navController: NavHostController,
    currentNavDest: NavDest,
    featuredImage: Image?,
    onItemClicked: (NavDest) -> Unit
) {
    Column {
        featuredImage?.let { FeaturedImage(image = it) }
        DrawerItems(currentNavDest = currentNavDest, onItemClicked = { onItemClicked(it) })
    }
}

@Composable
fun FeaturedImage(image: Image) {
    GlideImage(image.representations.medium, modifier = Modifier.height(150.dp))
}

@Composable
fun DrawerItems(
    currentNavDest: NavDest,
    onItemClicked: (NavDest) -> Unit
) {
    val items = remember { NavDest.values().filter { it.isTopLevelDest }.toList() }
    Column {
        items.forEach { navDest ->
            Button(onClick = { onItemClicked(navDest)}) {
                Text(text = navDest.drawerName)
            }
        }
    }
}