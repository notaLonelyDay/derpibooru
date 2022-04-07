package me.lonelyday.derpibooru.db.vo

import androidx.room.*
import me.lonelyday.api.models.ImageModel
import me.lonelyday.derpibooru.util.toLocalDateTime
import java.time.LocalDateTime

@Entity
data class Image(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,

    @ColumnInfo(name = "animated") val animated: Boolean,
    @ColumnInfo(name = "aspect_ratio") val aspectRatio: Float,
    @ColumnInfo(name = "comment_count") val commentCount: Int,
    @ColumnInfo(name = "created_at") val createdAt: LocalDateTime?,
    @ColumnInfo(name = "deletion_reason") val deletion_reason: String?,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "downvotes") val downvotes: Int,
    @ColumnInfo(name = "duplicate_of") val duplicate_of: Int?,
    @ColumnInfo(name = "duration") val duration: Float,
    @ColumnInfo(name = "faves") val faves: Int,
    @ColumnInfo(name = "first_seen_at") val first_seen_at: LocalDateTime?,
    @ColumnInfo(name = "format") val format: String,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "hidden_from_users") val hidden_from_users: Boolean,
    // TODO: what is intensities
//    @ColumnInfo(name = "intensities") val intensities: Object?,
    @ColumnInfo(name = "mime_type") val mime_type: String?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "orig_sha512_hash") val orig_sha512_hash: String?,
    @ColumnInfo(name = "processed") val processed: Boolean,
    @Embedded val representations: ImageRepresentations,
    @ColumnInfo(name = "score") val score: Int,
    @ColumnInfo(name = "sha512_hash") val sha512_hash: String,
    @ColumnInfo(name = "size") val size: Int,
    @ColumnInfo(name = "source_url") val source_url: String?,
    @ColumnInfo(name = "spoilered") val spoilered: Boolean,

    @ColumnInfo(name = "tag_count") val tag_count: Int,
    @ColumnInfo(name = "tag_ids") val tag_ids: List<Int>,
    @ColumnInfo(name = "tag_names") val tag_names: List<String>,

    @ColumnInfo(name = "thumbnails_generated") val thumbnails_generated: Boolean,
    @ColumnInfo(name = "updated_at") val updated_at: LocalDateTime?,
    @ColumnInfo(name = "uploader") val uploader: String?,
    @ColumnInfo(name = "uploader_id") val uploader_id: Int?,
    @ColumnInfo(name = "upvotes") val upvotes: Int,
    @ColumnInfo(name = "view_url") val view_url: String,
    @ColumnInfo(name = "width") val width: Int,
    @ColumnInfo(name = "wilson_score") val wilson_score: Float,
)

fun ImageModel.toImage(): Image {
    return Image(
        id = this.id,
        animated = this.animated,
        aspectRatio = this.aspectRatio,
        commentCount = this.commentCount,
        createdAt = this.createdAt?.toLocalDateTime(),
        deletion_reason = this.deletionReason,
        description = this.description,
        downvotes = this.downvotes,
        duplicate_of = this.duplicate_of,
        duration = this.duration,
        faves = this.faves,
        first_seen_at = this.first_seen_at?.toLocalDateTime() ,
        format = this.format,
        height = this.height,
        hidden_from_users = this.hidden_from_users,
        mime_type = this.mime_type,
        name = this.name,
        orig_sha512_hash = this.orig_sha512_hash,
        processed = this.processed,
        representations = this.representations.toImageRepresentations(),
        score = this.score,
        sha512_hash = this.sha512_hash,
        size = this.size,
        source_url = this.source_url,
        spoilered = this.spoilered,
        tag_count = this.tag_count,
        tag_ids = this.tag_ids,
        tag_names = this.tags,
        thumbnails_generated = this.thumbnails_generated,
        updated_at = this.updated_at?.toLocalDateTime(),
        uploader = this.uploader,
        uploader_id = this.uploader_id,
        upvotes = this.upvotes,
        view_url = this.view_url,
        width = this.width,
        wilson_score = this.wilson_score,
    )
}

data class ImageWithTags(
    @Embedded val image: Image,
    @Relation(parentColumn = "id", entityColumn = "id")
    val tags: List<Tag>
)

data class ImageRepresentations(
    @ColumnInfo(name = "thumb_tiny")
    val thumb_tiny: String?,
    @ColumnInfo(name = "thumb_small")
    val thumb_small: String?,
    @ColumnInfo(name = "thumb")
    val thumb: String?,
    @ColumnInfo(name = "tall")
    val tall: String?,
    @ColumnInfo(name = "small")
    val small: String?,
    @ColumnInfo(name = "medium")
    val medium: String?,
    @ColumnInfo(name = "large")
    val large: String?,
    @ColumnInfo(name = "full")
    val full: String?,
){

    fun getUrlBySize(size: Size): String? {
        return when (size) {
            Size.THUMB_TINY -> thumb_tiny
            Size.THUMB_SMALL -> thumb_small
            Size.THUMB -> thumb
            Size.TALL -> tall
            Size.SMALL -> small
            Size.MEDIUM -> medium
            Size.LARGE -> large
            Size.FULL -> full
        }
    }

    enum class Size {
        THUMB_TINY,
        THUMB_SMALL,
        THUMB,
        TALL,
        SMALL,
        MEDIUM,
        LARGE,
        FULL
    }
}

fun Map<String, String>.toImageRepresentations(): ImageRepresentations {
    return ImageRepresentations(
        thumb_tiny = this["thumb_tiny"],
        thumb_small = this["thumb_small"],
        thumb = this["thumb"],
        tall = this["tall"],
        small = this["small"],
        medium = this["medium"],
        large = this["large"],
        full = this["full"],
    )
}