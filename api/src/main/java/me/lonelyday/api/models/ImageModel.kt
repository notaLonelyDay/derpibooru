package me.lonelyday.api.models

import com.squareup.moshi.Json
import java.time.LocalDateTime
import java.util.*

data class ImageModel(
    @Json(name = "animated") val animated: Boolean,
    @Json(name = "aspect_ratio") val aspectRatio: Float,
    @Json(name = "comment_count") val commentCount: Int,
    @Json(name = "created_at") val createdAt: Date?,
    @Json(name = "deletion_reason") val deletionReason: String?,
    @Json(name = "description") val description: String,
    @Json(name = "downvotes") val downvotes: Int,
    @Json(name = "duplicate_of") val duplicate_of: Int?,
    @Json(name = "duration") val duration: Float,
    @Json(name = "faves") val faves: Int,
    @Json(name = "first_seen_at") val first_seen_at: Date?,
    @Json(name = "format") val format: String,
    @Json(name = "height") val height: Int,
    @Json(name = "hidden_from_users") val hidden_from_users: Boolean,
    @Json(name = "id") val id: Int,
    // TODO: what is intensities
//    @Json(name = "intensities") val intensities: Object?,
    @Json(name = "mime_type") val mime_type: String,
    @Json(name = "name") val name: String,
    @Json(name = "orig_sha512_hash") val orig_sha512_hash: String,
    @Json(name = "processed") val processed: Boolean,
    @Json(name = "representations") val representations: Map<String, String>,
    @Json(name = "score") val score: Int,
    @Json(name = "sha512_hash") val sha512_hash: String,
    @Json(name = "size") val size: Int,
    @Json(name = "source_url") val source_url: String,
    @Json(name = "spoilered") val spoilered: Boolean,
    @Json(name = "tag_count") val tag_count: Int,
    @Json(name = "tag_ids") val tag_ids: List<Int>,
    @Json(name = "tags") val tags: List<String>,
    @Json(name = "thumbnails_generated") val thumbnails_generated: Boolean,
    @Json(name = "updated_at") val updated_at: Date?,
    @Json(name = "uploader") val uploader: String,
    @Json(name = "uploader_id") val uploader_id: Int?,
    @Json(name = "upvotes") val upvotes: Int,
    @Json(name = "view_url") val view_url: String,
    @Json(name = "width") val width: Int,
    @Json(name = "wilson_score") val wilson_score: Float,
)

data class SearchImagesResponse(
    @Json(name = "images") val images: List<ImageModel>,
//    TODO: check what is interactions
//    @Json(name = "interactions") val interactions: List<Int>,
    @Json(name = "total") val total: Int,
)

data class FeaturedImageResponse(
    @Json(name = "image") val image: ImageModel,
//TODO    @Json(name = "interactions") val interactions: List<Int>,
)