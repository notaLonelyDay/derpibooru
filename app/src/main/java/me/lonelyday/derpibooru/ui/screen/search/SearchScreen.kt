package me.lonelyday.derpibooru.ui.screen.search

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import me.lonelyday.api.models.Query
import me.lonelyday.derpibooru.ui.search.SearchQueryFragment.Companion.DEFAULT_QUERY

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {

    Column {
        val query by viewModel.query.collectAsState(initial = DEFAULT_QUERY)
        QueryScreen(query = query, onQueryChanged = {
            viewModel.submitQuery(it)
        })


        val imagesWithTags = viewModel.imagesWithTags.collectAsLazyPagingItems()
        var isRefreshing by remember { mutableStateOf(false) }
        SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing), onRefresh = { imagesWithTags.refresh() }) {
            LazyColumn {
                if (imagesWithTags.itemCount != 0) {
                    items(imagesWithTags, { it.image.id }) { imageWithTags ->
                        imageWithTags?.let { ImageWithTagsItem(image = it) }
                    }
                } else {
                    item { Text("No results", Modifier.fillParentMaxSize()) }
                }


            }
            isRefreshing = when (imagesWithTags.loadState.refresh) {
                is LoadState.Loading -> true
                is LoadState.NotLoading -> false
                is LoadState.Error -> false
            }
        }
    }
}

@Composable
fun QueryScreen(
    query: Query,
    onQueryChanged: (Query) -> Unit
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = query.string,
        trailingIcon = {
            Icon(
                Icons.Filled.Clear,
                contentDescription = "clear text",
                modifier = Modifier
                    .offset(x = 10.dp)
                    .clickable { onQueryChanged(query.copy(string = "")) }
            )
        },
        onValueChange = { onQueryChanged(query.copy(string = it)) }
    )
}