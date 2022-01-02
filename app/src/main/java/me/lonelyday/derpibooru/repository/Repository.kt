package me.lonelyday.derpibooru.repository

import android.content.SharedPreferences
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import me.lonelyday.api.interfaces.DerpibooruService
import me.lonelyday.api.models.Query
import me.lonelyday.api.models.SearchTagsResponse
import me.lonelyday.derpibooru.DEFAULT_PAGE_SIZE
import me.lonelyday.derpibooru.db.DerpibooruDb
import me.lonelyday.derpibooru.db.vo.Image
import me.lonelyday.derpibooru.db.vo.Tag
import me.lonelyday.derpibooru.db.vo.toImage
import me.lonelyday.derpibooru.db.vo.toTag
import me.lonelyday.derpibooru.repository.paging.NetworkSearchImagesPagingSource

open class Repository(
    private val database: DerpibooruDb,
    private val service: DerpibooruService,
    private val sharedPreferences: SharedPreferences
) {

    // settings
    var perPage: Int = DEFAULT_PAGE_SIZE
    var key: String? = null
    var filterId: Int? = null

    init {
        refresh()
    }

    fun refresh() {
        perPage = sharedPreferences.getInt("page_size", DEFAULT_PAGE_SIZE)
        key = sharedPreferences.getString("key", null)
        filterId = sharedPreferences.getString("filter_id", null)?.toInt()
        // TODO: better way retrieving filterId
        updateService()
    }

    private fun updateService() {
        service.key = key
        service.filterId = filterId
    }


    suspend fun featuredImage() = service.featuredImage().image.toImage()

    fun searchImagesPaging(query: Query): Flow<PagingData<Image>> =
        Pager(
            PagingConfig(
                pageSize = perPage,
                initialLoadSize = perPage,
                enablePlaceholders = false,
            )
        ) {
            NetworkSearchImagesPagingSource(
                this, query
            )
        }.flow

    suspend fun searchImages(query: Query, page: Int, perPage: Int = this.perPage): List<Image> {
        val response = service.searchImages(query, page, perPage)
        return response.images.map {
            val image = it.toImage()
//            addTags(image)

            image
        }
    }

    // adds tags to image using loadTag
    private suspend fun addTags(image: Image) {
        val tagsList = emptyList<Tag>().toMutableList()
        for ((id, name) in image.tag_ids.zip(image.tag_names)) {
            loadTag(id, name)?.let { tagsList.add(it) }
        }
        image.tags = tagsList
    }

    suspend fun checkKey(key: String): Boolean {
        return service.checkKey(key = key)
    }

    suspend fun searchTags(query: String, page: Int? = null): SearchTagsResponse {
        return service.searchTags(
            query = query,
            page = page,
            perPage = perPage
        )
    }

    private suspend fun loadTag(id: Int, name: String): Tag? {
        var tag = database.tagDao().load(id)
        if (tag == null) {
            tag = searchTags(query = name).tags.getOrNull(0)?.toTag()
            tag?.let{
                database.tagDao().insert(it)
            }
        }
        return tag
    }
}