package me.lonelyday.derpibooru.ui.search.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.lonelyday.api.interfaces.DerpibooruService
import me.lonelyday.derpibooru.db.vo.Image
import me.lonelyday.derpibooru.db.vo.toImage
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

class NetworkPagingSource(
    val service: DerpibooruService,
    val query: String
) : PagingSource<Int, Image>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        try {
            val key = params.key ?: 0
            val next = key + 1
            val prev = if (key != 0) key - 1 else null


            val resp = service.searchImages(query, key, params.loadSize)
            val data = resp.images.map { it.toImage() }

            return LoadResult.Page(
                data = data,
                prevKey = prev,
                nextKey = next
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}