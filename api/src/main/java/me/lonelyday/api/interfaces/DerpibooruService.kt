package me.lonelyday.api.interfaces

import me.lonelyday.api.models.*

interface DerpibooruService {
    suspend fun featuredImage(): FeaturedImageResponse

    suspend fun searchImages(
        query: Query,
        page: Int,
        perPage: Int,
    ): SearchImagesResponse

    suspend fun searchTags(
        query: String,
        page: Int? = null,
        perPage: Int? = null
    ): SearchTagsResponse

    suspend fun fetchTag(slug: String): TagResponse

    suspend fun fetchFiltersUser(page: Int? = null): FiltersResponse

    suspend fun checkKey(key: String, page: Int? = null): Boolean
}