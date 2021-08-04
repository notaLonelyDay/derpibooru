package me.lonelyday.derpibooru.db.vo

import androidx.room.ColumnInfo
import me.lonelyday.api.models.TagModel

data class Tag(
    @ColumnInfo(name = "aliased_tag") val aliasedTag: String?,
    @ColumnInfo(name = "aliases") val aliases: List<String>,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "description") val description: String,
//TODO   @ColumnInfo(name = "dnp_entries") val dnp_entries: Array<?>,
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "images") val images: Int,
//TODO    @ColumnInfo(name = "implied_by_tags") val implied_by_tags: Array<?>,
//TODO    @ColumnInfo(name = "implied_tags") val implied_tags: Array<?>,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "name_in_namespace") val nameInNamespace: String,
    @ColumnInfo(name = "namespace") val namespace: String,
    @ColumnInfo(name = "short_description") val shortDescription: String,
    @ColumnInfo(name = "slug") val slug: String,
    @ColumnInfo(name = "spoiler_image_uri") val spoilerImageUri: String?,
)

fun TagModel.toTag(): Tag {
    return Tag(
        aliasedTag = this.aliasedTag,
        aliases = this.aliases,
        category = this.category,
        description = this.description,
        id = this.id,
        images = this.images,
        name = this.name,
        nameInNamespace = this.nameInNamespace,
        namespace = this.namespace,
        shortDescription = this.shortDescription,
        slug = this.slug,
        spoilerImageUri = this.spoilerImageUri,
    )
}
