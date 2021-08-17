package me.lonelyday.api.models

import com.squareup.moshi.Json

data class FilterModel(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "user_id") val userId: Int?,
    @Json(name = "user_count") val userCount: Int?,
    @Json(name = "system") val system: Boolean?,
    @Json(name = "public") val public: Boolean?,
    @Json(name = "spoilered_tag_ids") val spoileredTagIds: List<Int>?,
    @Json(name = "spoilered_complex") val spoileredComplex: String?,
    @Json(name = "hidden_tag_ids") val hiddenTagIds: List<Int>?,
    @Json(name = "hidden_complex") val hiddenComplex: String?,
)

data class FiltersResponse(
    @Json(name = "filters") val filters: List<FilterModel>,
    @Json(name = "total") val total: Int,
)