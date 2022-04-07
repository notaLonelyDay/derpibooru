package me.lonelyday.derpibooru.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.lonelyday.api.interfaces.DerpibooruService
import me.lonelyday.api.models.ImageModel
import me.lonelyday.api.models.Query
import me.lonelyday.api.models.SearchTagsResponse
import me.lonelyday.derpibooru.db.DerpibooruDb
import me.lonelyday.derpibooru.db.vo.*
import me.lonelyday.derpibooru.repository.paging.NetworkSearchImagesPagingSource
import me.lonelyday.derpibooru.util.toLocalDateTime

open class Repository(
    private val database: DerpibooruDb,
    private val service: DerpibooruService,
    private val settingsRepository: SettingsRepository
) {

    suspend fun featuredImage() = Mapper().map(service.featuredImage().image)

    fun searchImagesPaging(query: Query): Flow<PagingData<ImageWithTags>> = Pager(
        PagingConfig(
            pageSize = settingsRepository.pageSize,
            initialLoadSize = settingsRepository.pageSize,
            enablePlaceholders = false,
        )
    ) {
        NetworkSearchImagesPagingSource(
            this, query
        )
    }.flow

    suspend fun searchImages(
        query: Query,
        page: Int,
        perPage: Int = settingsRepository.pageSize
    ): List<ImageWithTags> {
        val response = service.searchImages(query, page, perPage)
        return response.images.map {
            val image = Mapper().map(it)

            image
        }
    }

    suspend fun checkKey(key: String): Boolean {
        return service.checkKey(key = key)
    }

    suspend fun searchTags(query: String, page: Int? = null): SearchTagsResponse {
        return service.searchTags(
            query = query,
            page = page,
            perPage = settingsRepository.pageSize
        )
    }

    private suspend fun loadTag(id: Int, name: String): Tag? {
        var tag = database.tagDao().load(id)
        if (tag == null) {
            tag = searchTags(query = name).tags.getOrNull(0)?.toTag()
            tag?.let {
                database.tagDao().insert(it)
            }
        }
        return tag
    }

    // TODO use it
    inner class Mapper {
        suspend fun map(image: ImageModel): ImageWithTags {
            val tagsFromDb = database.tagDao().loadMany(image.tag_ids)
            val tags = image.tag_ids.zip(image.tags).map { (id, name) ->
                tagsFromDb.firstOrNull { it.id == id }
                    ?: Tag(
                        id = id,
                        name = name,
                    )
            }
            return ImageWithTags(
                image = image.toImage(),
                tags = tags
            )
        }
    }
}