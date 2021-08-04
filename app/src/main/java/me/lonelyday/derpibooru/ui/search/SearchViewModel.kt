package me.lonelyday.derpibooru.ui.search

import androidx.lifecycle.ViewModel
import me.lonelyday.api.getRestController
import me.lonelyday.derpibooru.ui.search.paging.NetworkPageRepository

class SearchViewModel: ViewModel() {
    val service = getRestController().getService()
    val repository = NetworkPageRepository(service)
    val flow = repository.searchImages("safe", 50)
}