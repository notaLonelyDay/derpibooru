package me.lonelyday.api.controller

import me.lonelyday.api.interfaces.DerpibooruApi
import me.lonelyday.api.interfaces.DerpibooruService
import me.lonelyday.api.models.ImageSearchModel

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
    ): ImageSearchModel {
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


}