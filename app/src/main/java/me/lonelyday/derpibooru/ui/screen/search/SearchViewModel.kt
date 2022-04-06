package me.lonelyday.derpibooru.ui.screen.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import me.lonelyday.api.models.Query
import me.lonelyday.derpibooru.db.vo.Image
import me.lonelyday.derpibooru.repository.Repository
import me.lonelyday.derpibooru.ui.search.SearchQueryFragment
import me.lonelyday.derpibooru.ui.search.SearchQueryFragment.Companion.DEFAULT_QUERY
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repo: Repository
) : ViewModel() {
    companion object {
        const val KEY_QUERY = "query"
    }

    val query: Query
        get() = savedStateHandle[KEY_QUERY]!!

    init {
        if (!savedStateHandle.contains(KEY_QUERY)) {
            savedStateHandle.set<Query>(KEY_QUERY, DEFAULT_QUERY)
        }
    }

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)
    val images = flowOf(
        clearListCh.receiveAsFlow().map { PagingData.empty<Image>() },
        savedStateHandle.getLiveData<Query>(KEY_QUERY)
            .asFlow()
            .flatMapLatest { repo.searchImagesPaging(it) }
            .cachedIn(viewModelScope)
    ).flattenMerge(2)

    fun submitQuery(query: Query) {
        if (savedStateHandle.get<Query>(KEY_QUERY) != query) {
            clearListCh.trySend(Unit).isSuccess
            savedStateHandle.set<Query>(KEY_QUERY, query)
        }
    }
}