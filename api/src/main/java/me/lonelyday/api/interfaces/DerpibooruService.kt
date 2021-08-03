package me.lonelyday.api.interfaces

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import me.lonelyday.api.models.ImageSearchModel
import retrofit2.Call

interface DerpibooruService {
    suspend fun searchImages(
        query: String,
        page: Int,
        perPage: Int,
        sortDirection: String? = null,
        sortField: String? = null
    ): ImageSearchModel
}