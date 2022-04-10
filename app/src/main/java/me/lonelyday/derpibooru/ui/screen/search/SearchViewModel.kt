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
import me.lonelyday.derpibooru.db.vo.ImageWithTags
import me.lonelyday.derpibooru.repository.Repository
import me.lonelyday.derpibooru.repository.SettingsRepository
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repo: Repository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    companion object {
        const val KEY_QUERY = "query"
    }

//    private val query = savedStateHandle.getLiveData<Query>(KEY_QUERY).asFlow()

    init {
        if (!savedStateHandle.contains(KEY_QUERY)) {
            savedStateHandle.set<Query>(KEY_QUERY, settingsRepository.lastSearchedQuery)
        }
    }

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)
    val imagesWithTags = flowOf(
        clearListCh.receiveAsFlow().map { PagingData.empty<ImageWithTags>() },
        savedStateHandle.getLiveData<Query>(KEY_QUERY)
            .asFlow()
            .debounce(500)
            .flatMapLatest { repo.searchImagesPaging(it) }
            .cachedIn(viewModelScope)
    ).flattenMerge(2)

    fun searchByQuery(query: Query) {
        if (savedStateHandle.get<Query>(KEY_QUERY) != query) {
//            clearListCh.trySend(Unit).isSuccess
            savedStateHandle.set<Query>(KEY_QUERY, query)
        }
    }
}