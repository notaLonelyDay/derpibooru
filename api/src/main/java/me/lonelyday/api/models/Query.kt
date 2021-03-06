package me.lonelyday.api.models

import kotlinx.serialization.*


import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
@Serializable
data class Query(
    val string: String,
    val sortField: SortField? = null,
    val sortDirection: SortDirection? = null,
): Parcelable {
    override fun toString(): String {
        return string
    }
}

enum class SortField(
    val value: String,
    description: String
) {
    Id("id", "image ID"),
    UpdatedAt("updated_at", "last modification date"),
    FirstSeenAt("first_seen_at", "initial post date"),
    AspectRatio("aspect_ratio", "aspect ratio"),
    WilsonScore("wilson_score", "Wilson score"),
    // TODO continue
}

enum class SortDirection(
    val value: String
) {
    Ascending("asc"),
    Descending("desc")
}