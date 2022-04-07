package me.lonelyday.derpibooru.db.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.time.LocalDateTime

@Entity
data class Image(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,

    @ColumnInfo(name = "animated") val animated: Boolean,
    @ColumnInfo(name = "aspect_ratio") val aspectRatio: Float,
    @ColumnInfo(name = "comment_count") val commentCount: Int,
    @ColumnInfo(name = "created_at") val createdAt: LocalDateTime,
    @ColumnInfo(name = "deletion_reason") val deletion_reason: String?,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "downvotes") val downvotes: Int,
    @ColumnInfo(name = "duplicate_of") val duplicate_of: Int?,
    @ColumnInfo(name = "duration") val duration: Float,
    @ColumnInfo(name = "faves") val faves: Int,
    @ColumnInfo(name = "first_seen_at") val first_seen_at: LocalDateTime,
    @ColumnInfo(name = "format") val format: String,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "hidden_from_users") val hidden_from_users: Boolean,
    // TODO: what is intensities
//    @ColumnInfo(name = "intensities") val intensities: Object?,
    @ColumnInfo(name = "mime_type") val mime_type: String?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "orig_sha512_hash") val orig_sha512_hash: String?,
    @ColumnInfo(name = "processed") val processed: Boolean,
    @ColumnInfo(name = "representations") val representations: Map<String, String>,
    @ColumnInfo(name = "score") val score: Int,
    @ColumnInfo(name = "sha512_hash") val sha512_hash: String,
    @ColumnInfo(name = "size") val size: Int,
    @ColumnInfo(name = "source_url") val source_url: String?,
    @ColumnInfo(name = "spoilered") val spoilered: Boolean,

    @ColumnInfo(name = "thumbnails_generated") val thumbnails_generated: Boolean,
    @ColumnInfo(name = "updated_at") val updated_at: LocalDateTime,
    @ColumnInfo(name = "uploader") val uploader: String?,
    @ColumnInfo(name = "uploader_id") val uploader_id: Int?,
    @ColumnInfo(name = "upvotes") val upvotes: Int,
    @ColumnInfo(name = "view_url") val view_url: String,
    @ColumnInfo(name = "width") val width: Int,
    @ColumnInfo(name = "wilson_score") val wilson_score: Float,

    @Relation(parentColumn = "id", entityColumn = "id")
    @ColumnInfo(name = "tags") val tags: List<Tag>,
)
