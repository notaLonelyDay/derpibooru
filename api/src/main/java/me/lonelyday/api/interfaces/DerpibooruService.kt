package me.lonelyday.api.interfaces

import me.lonelyday.api.models.SearchImagesResponse
import me.lonelyday.api.models.SearchTagsResponse
import me.lonelyday.api.models.TagResponse

interface DerpibooruService {
    suspend fun searchImages(
        query: String,
        page: Int,
        perPage: Int,
        sortDirection: String? = null,
        sortField: String? = null
    ): SearchImagesResponse

    suspend fun searchTags(
        query: String,
        page: Int? = null,
        perPage: Int? = null
    ): SearchTagsResponse

    suspend fun fetchTag(slug: String): TagResponse
}