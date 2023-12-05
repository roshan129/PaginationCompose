package com.roshanadke.paginationdemo.data.network

import com.roshanadke.paginationdemo.data.dto.QuotesMainDto
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface QuotesApiService {

    companion object {
        //const val BASE_URL = "https://api.quotable.io/quotes?page=2&limit=10"
        const val BASE_URL = "https://api.quotable.io/"
    }

    @GET("quotes")
    suspend fun getQuotes(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): QuotesMainDto


}