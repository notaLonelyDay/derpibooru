package me.lonelyday.derpibooru.db.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import me.lonelyday.api.models.TagModel

@Entity(tableName = "tags")
data class Tag(
    /** Necessary fields **/
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,

    /** Api fields **/
    @ColumnInfo(name = "aliased_tag") val aliasedTag: String? = null,
//  todo  @ColumnInfo(name = "aliases") val aliases: List<String>,
    @ColumnInfo(name = "category") val category: String? = null,
    @ColumnInfo(name = "description") val description: String? = null,
//TODO   @ColumnInfo(name = "dnp_entries") val dnp_entries: Array<?>,
    @ColumnInfo(name = "images") val images: Int? = null,
//TODO    @ColumnInfo(name = "implied_by_tags") val implied_by_tags: Array<?>,
//TODO    @ColumnInfo(name = "implied_tags") val implied_tags: Array<?>,
    @ColumnInfo(name = "name_in_namespace") val nameInNamespace: String? = null,
    @ColumnInfo(name = "namespace") val namespace: String? = null,
    @ColumnInfo(name = "short_description") val shortDescription: String? = null,
    @ColumnInfo(name = "slug") val slug: String? = null,
    @ColumnInfo(name = "spoiler_image_uri") val spoilerImageUri: String? = null,

    /** Local fields **/
    @ColumnInfo(name = "have_full_info") val haveFullInfo: Boolean = false
)

fun TagModel.toTag(): Tag {
    return Tag(
        aliasedTag = this.aliasedTag,
//        aliases = this.aliases,
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

        haveFullInfo = true
    )
}
