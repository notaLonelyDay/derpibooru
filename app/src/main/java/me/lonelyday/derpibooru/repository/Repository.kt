package me.lonelyday.derpibooru.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.lonelyday.api.interfaces.DerpibooruService
import me.lonelyday.api.models.ImageModel
import me.lonelyday.api.models.Query
import me.lonelyday.api.models.SearchTagsResponse
import me.lonelyday.derpibooru.db.DerpibooruDb
import me.lonelyday.derpibooru.db.vo.Image
import me.lonelyday.derpibooru.db.vo.Tag
import me.lonelyday.derpibooru.db.vo.toImage
import me.lonelyday.derpibooru.db.vo.toTag
import me.lonelyday.derpibooru.repository.paging.NetworkSearchImagesPagingSource
import java.time.LocalDateTime

open class Repository(
    private val database: DerpibooruDb,
    private val service: DerpibooruService,
    private val settingsRepository: SettingsRepository
) {

    suspend fun featuredImage() = service.featuredImage().image.toImage()

    fun searchImagesPaging(query: Query): Flow<PagingData<Image>> = Pager(
        PagingConfig(
            pageSize = settingsRepository.pageSize,
            initialLoadSize = settingsRepository.pageSize,
            enablePlaceholders = false,
        )
    ) {
        NetworkSearchImagesPagingSource(
            this, query
        )
    }.flow

    suspend fun searchImages(
        query: Query,
        page: Int,
        perPage: Int = settingsRepository.pageSize
    ): List<Image> {
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
            perPage = settingsRepository.pageSize
        )
    }

    private suspend fun loadTag(id: Int, name: String): Tag? {
        var tag = database.tagDao().load(id)
        if (tag == null) {
            tag = searchTags(query = name).tags.getOrNull(0)?.toTag()
            tag?.let {
                database.tagDao().insert(it)
            }
        }
        return tag
    }

    // TODO use it
    inner class Mapper {
        fun map(image: ImageModel): Image {
            return Image(
                id = image.id,
                animated = image.animated,
                aspectRatio = image.aspectRatio,
                commentCount = image.commentCount,
                createdAt = LocalDateTime.ofEpochSecond(image.createdAt, 0, null),
                deletion_reason = image.deletionReason,
                description = image.description,
                downvotes = image.downvotes,
                duplicate_of = image.duplicate_of,
                duration = image.duration,
                faves = image.faves,
                first_seen_at = LocalDateTime.ofEpochSecond(image.first_seen_at, 0, null),
                format = image.format,
                height = image.height,
                hidden_from_users = image.hidden_from_users,
                mime_type = image.mime_type,
                name = image.name,
                orig_sha512_hash = image.orig_sha512_hash,
                processed = image.processed,
                representations = image.representations,
                score = image.score,
                sha512_hash = image.sha512_hash,
                size = image.size,
                source_url = image.source_url,
                spoilered = image.spoilered,
                tag_count = image.tag_count,
                tag_ids = image.tag_ids,
                tag_names = image.tags,
                thumbnails_generated = image.thumbnails_generated,
                updated_at = image.updated_at.time,
                uploader = image.uploader,
                uploader_id = image.uploader_id,
                upvotes = image.upvotes,
                view_url = image.view_url,
                width = image.width,
                wilson_score = image.wilson_score,
            )
        }
    }
}