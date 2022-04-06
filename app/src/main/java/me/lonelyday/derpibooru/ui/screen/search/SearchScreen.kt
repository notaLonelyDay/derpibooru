package me.lonelyday.derpibooru.ui.screen.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {

    Column {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = {
                viewModel.submitQuery(viewModel.query.copy(string = it))
            })
        repeat(10) {
            ImageItemPreview()
        }
    }

}