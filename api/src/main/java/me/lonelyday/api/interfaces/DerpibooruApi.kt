package me.lonelyday.api.interfaces

import me.lonelyday.api.models.FeaturedImageResponse
import me.lonelyday.api.models.SearchImagesResponse
import me.lonelyday.api.models.SearchTagsResponse
import me.lonelyday.api.models.TagResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DerpibooruApi {
    @GET("images/featured")
    suspend fun featuredImage(): FeaturedImageResponse

    @GET("search/images")
    suspend fun searchImages(
        @Query("key") key: String?,
        @Query("filter_id") filterId: Int?,
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int?,
        @Query("q") query: String,
        @Query("sd") sortDirection: String?,
        @Query("sf") sortField: String?,
    ): SearchImagesResponse

    @GET("search/tags")
    suspend fun searchTags(
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int?,
        @Query("q") query: String,
    ): SearchTagsResponse

    @GET("tags/{slug}")
    suspend fun fetchTag(@Path("slug") slug: String): TagResponse
}