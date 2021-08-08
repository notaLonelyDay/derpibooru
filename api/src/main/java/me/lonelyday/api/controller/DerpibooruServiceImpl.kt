package me.lonelyday.api.controller

import me.lonelyday.api.interfaces.DerpibooruApi
import me.lonelyday.api.interfaces.DerpibooruService
import me.lonelyday.api.models.*

class DerpibooruServiceImpl(
    private val api: DerpibooruApi
) : DerpibooruService {

    override var filterId: Int? = null
    override var key: String? = null

    override suspend fun featuredImage() = api.featuredImage()

    override suspend fun searchImages(
        query: Query,
        page: Int,
        perPage: Int
    ): SearchImagesResponse {
        return api.searchImages(
            key = key,
            filterId = filterId,
            page = page,
            perPage = perPage,
            query = query.string,
            sortDirection = query.sortDirection?.name,
            sortField = query.sortField?.name,
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