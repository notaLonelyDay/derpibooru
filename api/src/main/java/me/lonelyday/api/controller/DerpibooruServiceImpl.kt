package me.lonelyday.api.controller

import me.lonelyday.api.interfaces.DerpibooruApi
import me.lonelyday.api.interfaces.DerpibooruService
import me.lonelyday.api.models.SearchImagesResponse
import me.lonelyday.api.models.SearchTagsResponse
import me.lonelyday.api.models.TagResponse

class DerpibooruServiceImpl(
    private val api: DerpibooruApi
) : DerpibooruService {
    var filterId: Int? = null
    var key: String? = null


    override suspend fun searchImages(
        query: String,
        page: Int,
        perPage: Int,
        sortDirection: String?,
        sortField: String?
    ): SearchImagesResponse {
        return api.searchImages(
            key = key,
            filterId = filterId,
            page = page,
            perPage = perPage,
            query = query,
            sortDirection = sortDirection,
            sortField = sortField,
        )
    }

    override suspend fun searchTags(query: String, page: Int?, perPage: Int?): SearchTagsResponse {
        return api.searchTags(
            query = query,
            page = page,
            perPage = perPage
        )
    }

    override suspend fun fetchTag(slug: String): TagResponse {
        return api.fetchTag(slug = slug)
    }


}