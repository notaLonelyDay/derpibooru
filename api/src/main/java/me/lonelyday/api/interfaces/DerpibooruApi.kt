package me.lonelyday.api.interfaces

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import me.lonelyday.api.models.ImageSearchModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DerpibooruApi {

    @GET("search/images")
    suspend fun searchImages(
        @Query("key") key: String?,
        @Query("filter_id") filterId: Int?,
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int?,
        @Query("q") query: String,
        @Query("sd") sortDirection: String?,
        @Query("sf") sortField: String?,
    ): ImageSearchModel
}