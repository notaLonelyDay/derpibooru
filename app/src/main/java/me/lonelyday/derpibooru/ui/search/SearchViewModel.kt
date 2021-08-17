package me.lonelyday.derpibooru.ui.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.lonelyday.api.models.Query
import me.lonelyday.derpibooru.repository.Repository
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {

    init {
        repo.refresh()
    }

    fun getImagesPagingDataFlow(query: Query) = repo.searchImagesPaging(query)
}