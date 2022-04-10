package me.lonelyday.derpibooru.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import me.lonelyday.derpibooru.ui.theme.DerpibooruTheme

@Composable
fun MainScreen() {
    DerpibooruTheme {

        val viewModel = hiltViewModel<MainScreenViewModel>()
        val featuredImage by viewModel.featuredImageWithTags.collectAsState()
        LaunchedEffect(key1 = null, block = { viewModel.loadFeaturedImage() })
        val navController = rememberNavController()
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        var currentNavDest by remember { mutableStateOf(NavDest.startDest) }


        Scaffold(
            scaffoldState = scaffoldState,
            modifier = Modifier.fillMaxSize(),
            content = {
                GlobalNavHost(
                    navController = navController,
                    onNavDestChanged = { currentNavDest = it }
                )
            },
            topBar = {
                TopAppBar(
                    title = { Text(currentNavDest.appBarName) },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                scaffoldState.drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(Icons.Filled.Menu, "")
                        }
                    },
                )
            },
            drawerContent = {
                NavDrawer(
                    navController = navController,
                    currentNavDest = currentNavDest,
                    featuredImage = featuredImage?.image,
                    onItemClicked = {
                        if(navController.currentDestination?.route != it.navName)
                            navController.navigate(it.navName)
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    }
                )
            },
        )
    }
}