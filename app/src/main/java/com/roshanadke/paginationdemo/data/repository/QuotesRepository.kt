package com.roshanadke.paginationdemo.data.repository

import com.roshanadke.paginationdemo.data.dto.QuotesMainDto
import kotlinx.coroutines.flow.Flow

interface QuotesRepository {

    fun getQuotes(page: Int, limit: Int): Flow<QuotesMainDto>

    suspend fun getPagingQuotes(page: Int, limit: Int): Result<QuotesMainDto>


}