package me.lonelyday.derpibooru.ui.screen.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import me.lonelyday.derpibooru.ui.search.SearchQueryFragment.Companion.DEFAULT_QUERY

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {

    Column {
        val query by viewModel.query.collectAsState(initial = DEFAULT_QUERY)
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = query.string,
            onValueChange = {
                viewModel.submitQuery(query.copy(string = it))
            }
        )
        val imagesWithTags = viewModel.imagesWithTags.collectAsLazyPagingItems()
        LazyColumn {
            items(imagesWithTags, {it.image.id}) { imageWithTags ->
                imageWithTags?.let { ImageWithTagsItem(image = it) }
            }
        }
    }
}

@Composable
fun QueryScreen() {

}