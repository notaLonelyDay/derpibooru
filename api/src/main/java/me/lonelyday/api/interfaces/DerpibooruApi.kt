package me.lonelyday.api.interfaces

import me.lonelyday.api.models.*
import okhttp3.ResponseBody
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

    @GET("filters/user")
    suspend fun fetchFiltersUser(
        @Query("key") key: String?,
        @Query("page") page: Int?,
    ): FiltersResponse

    @GET("filters/user")
    suspend fun fetchFiltersUserRaw(
        @Query("key") key: String?,
        @Query("page") page: Int?,
    ): ResponseBody

}