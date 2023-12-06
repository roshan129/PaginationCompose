package com.roshanadke.paginationdemo.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.roshanadke.paginationdemo.data.dto.QuotesMainDto
import com.roshanadke.paginationdemo.data.dto.Result
import com.roshanadke.paginationdemo.paging.QuotesPagingSource
import kotlinx.coroutines.flow.Flow

interface QuotesRepository {

    fun getQuotes(page: Int, limit: Int): Flow<QuotesMainDto>

    fun getPagerQuotes(): LiveData<PagingData<Result>>

}