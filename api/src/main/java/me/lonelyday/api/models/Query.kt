package me.lonelyday.api.models

data class Query(
    val string: String,
    val sortField: SortField? = null,
    val sortDirection: SortDirection? = null,
)

enum class SortField(
    value: String,
    description: String
) {
    Id("id", "image ID"),
    UpdatedAt("updated_at", "last modification date"),
    FirstSeenAt("first_seen_at", "initial post date"),
    AspectRatio("aspect_ratio", "aspect ratio")
    // TODO continue
}

enum class SortDirection(
    val value: String
) {
    Ascending("asc"),
    Descending("desc")
}