package me.lonelyday.derpibooru.repository

import android.content.SharedPreferences
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.lonelyday.api.interfaces.DerpibooruService
import me.lonelyday.api.models.Query
import me.lonelyday.derpibooru.db.vo.Image
import me.lonelyday.derpibooru.db.vo.toImage
import me.lonelyday.derpibooru.repository.paging.NetworkSearchImagesPagingSource

class ImagesRepository(
    private val service: DerpibooruService,
    sharedPreferences: SharedPreferences
) : Repository(
    service,
    sharedPreferences
) {

    suspend fun featuredImage() = service.featuredImage().image.toImage()

    fun searchImagesPaging(query: Query): Flow<PagingData<Image>> =
        Pager(
            PagingConfig(pageSize = perPage)
        ) {
            NetworkSearchImagesPagingSource(
                this, query
            )
        }.flow

    suspend fun searchImages(query: Query, page: Int, perPage: Int = this.perPage): List<Image> {
        val response = service.searchImages(query, page, perPage)
        return response.images.map { it.toImage() }
    }
}