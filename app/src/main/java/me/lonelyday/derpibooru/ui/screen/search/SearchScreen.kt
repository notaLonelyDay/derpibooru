package me.lonelyday.derpibooru.ui.screen.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
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
        var isRefreshing by remember { mutableStateOf(false) }
        SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing), onRefresh = { imagesWithTags.refresh() }) {

            LazyColumn {
                items(imagesWithTags, { it.image.id }) { imageWithTags ->
                    imageWithTags?.let { ImageWithTagsItem(image = it) }
                }
            }

            imagesWithTags.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        isRefreshing = true
                    }
                    loadState.append is LoadState.Loading -> {
                    }
                    loadState.refresh is LoadState.Error -> {
                    }
                    loadState.append is LoadState.Error -> {
                    }
                }
            }
        }
    }
}

@Composable
fun QueryScreen() {

}