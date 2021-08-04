package me.lonelyday.derpibooru.ui.search.paging

import android.accessibilityservice.GestureDescription
import androidx.paging.Pager
import androidx.paging.PagingConfig
import me.lonelyday.api.interfaces.DerpibooruService

class NetworkPageRepository(
    private val service: DerpibooruService
) {
    fun searchImages(query: String, pageSize: Int) = Pager(
        PagingConfig(pageSize = pageSize)
    ) {
        NetworkPagingSource(
            service, query
        )
    }.flow
}