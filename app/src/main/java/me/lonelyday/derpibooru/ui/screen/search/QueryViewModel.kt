package me.lonelyday.derpibooru.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import me.lonelyday.api.models.Query
import me.lonelyday.derpibooru.repository.SettingsRepository
import javax.inject.Inject

@HiltViewModel
class QueryViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val query: MutableStateFlow<Query> = MutableStateFlow(settingsRepository.lastSearchedQuery)
    val queryFlow = query
        .asStateFlow()


    private var querySavingJob: Job? = null
    fun submitQuery(query: Query) {
        querySavingJob?.cancel()
        this.query.value = query
        querySavingJob = viewModelScope.launch {
            settingsRepository.lastSearchedQuery = query
        }
    }
}