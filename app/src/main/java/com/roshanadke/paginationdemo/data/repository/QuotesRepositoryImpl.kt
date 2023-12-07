package com.roshanadke.paginationdemo.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.roshanadke.paginationdemo.data.dto.QuotesMainDto
import com.roshanadke.paginationdemo.data.network.QuotesApiService
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

    override suspend fun getPagingQuotes(page: Int, limit: Int): Result<QuotesMainDto> {
        return try {
            val result = apiService.getQuotes(page, limit)
            Result.success(result)
        }catch (e: Exception) {
            Result.failure(e)
        }
    }

}