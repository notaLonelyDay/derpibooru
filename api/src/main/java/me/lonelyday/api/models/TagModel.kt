package me.lonelyday.api.models

import com.squareup.moshi.Json

data class TagModel(
    @Json(name = "aliased_tag") val aliasedTag: String?,
    @Json(name = "aliases") val aliases: List<String>,
    @Json(name = "category") val category: String,
    @Json(name = "description") val description: String,
//TODO   @Json(name = "dnp_entries") val dnp_entries: Array<?>,
    @Json(name = "id") val id: Int,
    @Json(name = "images") val images: Int,
//TODO    @Json(name = "implied_by_tags") val implied_by_tags: Array<?>,
//TODO    @Json(name = "implied_tags") val implied_tags: Array<?>,
    @Json(name = "name") val name: String,
    @Json(name = "name_in_namespace") val nameInNamespace: String,
    @Json(name = "namespace") val namespace: String,
    @Json(name = "short_description") val shortDescription: String,
    @Json(name = "slug") val slug: String,
    @Json(name = "spoiler_image_uri") val spoilerImageUri: String?,
)

data class TagResponse(
    @Json(name = "tag") val tag: TagModel,
)

data class SearchTagsResponse(
    @Json(name = "tags") val tags: List<TagModel>,
    @Json(name = "total") val total: Int
)