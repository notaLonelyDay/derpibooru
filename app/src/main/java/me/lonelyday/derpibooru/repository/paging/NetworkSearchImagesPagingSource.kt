package me.lonelyday.derpibooru.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.lonelyday.api.models.Query
import me.lonelyday.derpibooru.FIRST_PAGE_NUMBER
import me.lonelyday.derpibooru.db.vo.Image
import me.lonelyday.derpibooru.repository.Repository
import retrofit2.HttpException
import java.io.IOException

class NetworkSearchImagesPagingSource(
    private val repo: Repository,
    private val query: Query
) : PagingSource<Int, Image>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        try {
            val key = params.key ?: FIRST_PAGE_NUMBER
            val next = key + 1
            val prev = if (key != FIRST_PAGE_NUMBER) key - 1 else null


            val data = repo.searchImages(query, key, params.loadSize)
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