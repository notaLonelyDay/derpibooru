package me.lonelyday.derpibooru.ui.screen.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import me.lonelyday.api.models.Query
import me.lonelyday.derpibooru.INITIAL_QUERY

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel(),
    queryViewModel: QueryViewModel = hiltViewModel()
) {

    Column {
        val query by queryViewModel.queryFlow.collectAsState(initial = INITIAL_QUERY)
        LaunchedEffect(key1 = query, block = {viewModel.searchByQuery(query)})

        QueryScreen()

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
    viewModel: QueryViewModel = hiltViewModel()
) {

    ConstraintLayout(Modifier.fillMaxWidth()) {
        val (textField, button) = createRefs()
        val query by viewModel.queryFlow.collectAsState(initial = INITIAL_QUERY)

        TextField(
            modifier = Modifier.constrainAs(textField) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(button.start)
                width = Dimension.fillToConstraints
            },
            value = query.string,
            trailingIcon = {
                Icon(
                    Icons.Filled.Clear,
                    contentDescription = "clear text",
                    modifier = Modifier
                        .offset(x = 5.dp)
                        .clickable { viewModel.submitQuery(query.copy(string = "")) }
                )
            },
            onValueChange = { viewModel.submitQuery(query.copy(string = it)) }
        )
        Button(
            onClick = {

            },
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
                width = Dimension.wrapContent
            }
        ) {
            Text("Filters")
        }
    }
}

@Composable
@Preview
fun Filters(){

}

@Preview
@Composable
fun QueryScreenPreview() {
//    QueryScreen(query = Query("Cleartext"), onQueryChanged = {})
}