package me.lonelyday.derpibooru.ui.search

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import me.lonelyday.api.getRestController
import me.lonelyday.api.models.Query
import me.lonelyday.derpibooru.db.vo.Image
import me.lonelyday.derpibooru.repository.ImagesRepository
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repoImages: ImagesRepository
) : ViewModel() {

    fun getImagesPagingDataFlow(query: Query) = repoImages.searchImagesPaging(query)
}