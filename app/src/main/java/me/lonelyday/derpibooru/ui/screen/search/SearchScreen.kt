package me.lonelyday.derpibooru.ui.screen.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun SearchScreen(
    navController: NavController
) {
    Text(text = "SearchScreen")
    Column() {
        repeat(10) {
            ImageItemPreview()
        }
    }


}