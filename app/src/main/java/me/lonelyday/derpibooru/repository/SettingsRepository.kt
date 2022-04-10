package me.lonelyday.derpibooru.repository

import android.content.SharedPreferences
import me.lonelyday.derpibooru.DEFAULT_PAGE_SIZE
import javax.inject.Inject

class SettingsRepository(
    private val sharedPreferences: SharedPreferences
) {
    val pageSize: Int
        get() = sharedPreferences.getInt("page_size", DEFAULT_PAGE_SIZE)

    val key: String?
        get() = sharedPreferences.getString("key", null)

    val filterId: Int?
        get() = sharedPreferences.getString("filter_id", null)?.toInt()

}