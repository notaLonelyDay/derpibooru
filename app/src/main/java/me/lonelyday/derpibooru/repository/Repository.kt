package me.lonelyday.derpibooru.repository

import android.content.SharedPreferences
import me.lonelyday.api.interfaces.DerpibooruService
import me.lonelyday.derpibooru.DEFAULT_PAGE_SIZE

open class Repository(
    private val service: DerpibooruService,
    private val sharedPreferences: SharedPreferences
) {

    // settings
    var perPage: Int = DEFAULT_PAGE_SIZE
    var key: String? = null
    var filterId: Int? = null

    init {
        refresh()
    }

    fun refresh() {
        perPage = sharedPreferences.getInt("page_size", DEFAULT_PAGE_SIZE)
        key = sharedPreferences.getString("key", null)
        filterId = sharedPreferences.getString("filter_id", null)?.toInt()
        // TODO: better way retrieving filterId

    }

    private fun updateService(){
        service.key = key
        service.filterId = filterId
    }
}