package me.lonelyday.derpibooru.repository

import android.content.SharedPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.lonelyday.api.models.Query
import me.lonelyday.derpibooru.DEFAULT_PAGE_SIZE
import me.lonelyday.derpibooru.INITIAL_QUERY

class SettingsRepository(
    private val sharedPreferences: SharedPreferences
) {
    val pageSize: Int
        get() = sharedPreferences.getInt("page_size", DEFAULT_PAGE_SIZE)


    var key: String?
        get() = sharedPreferences.getString(KEY_KEY, null)
        set(value) = sharedPreferences.edit().putString(KEY_KEY, value).apply()

    val filterId: Int?
        get() = sharedPreferences.getString("filter_id", null)?.toInt()

    /**
     * Must be called asynchronously
     */
    var lastSearchedQuery: Query
        set(value) {
            val encoded = Json.encodeToString(value)
            sharedPreferences.edit()
                .putString(LAST_SEARCH_QUERY_KEY, encoded)
                .apply()
        }
        get() {
            val encoded = sharedPreferences.getString(LAST_SEARCH_QUERY_KEY, null)
            return encoded?.let { Json.decodeFromString(Query.serializer(), it) } ?: INITIAL_QUERY
        }


    private companion object {
        const val LAST_SEARCH_QUERY_KEY = "last_search_query"
        const val KEY_KEY = "key"
    }
}