package com.roshanadke.paginationdemo.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.roshanadke.paginationdemo.data.dto.QuotesMainDto
import com.roshanadke.paginationdemo.data.network.QuotesApiService
import com.roshanadke.paginationdemo.paging.QuotesPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class QuotesRepositoryImpl(
    private val apiService: QuotesApiService
) : QuotesRepository {

    override fun getQuotes(page: Int, limit: Int): Flow<QuotesMainDto> = flow {
        try {
            val result = apiService.getQuotes(page, limit)
            emit(result)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getPagerQuotes() = Pager(
        config = PagingConfig(10, maxSize = 100),
        pagingSourceFactory = { QuotesPagingSource(apiService) }
    ).liveData
}