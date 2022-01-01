package me.lonelyday.api.controller

import me.lonelyday.api.interfaces.DerpibooruApi
import me.lonelyday.api.interfaces.DerpibooruService
import me.lonelyday.api.models.*
import retrofit2.HttpException

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
            query = query.toString(),
            sortDirection = query.sortDirection?.value,
            sortField = query.sortField?.value,
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

    override suspend fun fetchFiltersUser(page: Int?): FiltersResponse {
        return api.fetchFiltersUser(key = key, page = page)
    }

    override suspend fun checkKey(key: String, page: Int?): Boolean {
        return try {
            api.fetchFiltersUserRaw(key, page)
            true
        } catch (exception: HttpException){
            if(exception.code() == 403)
                false
            else
                throw exception
        }
    }

}