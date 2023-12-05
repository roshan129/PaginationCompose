package com.roshanadke.paginationdemo.ui.viewmodels

import com.roshanadke.paginationdemo.data.network.QuotesApiService
import com.roshanadke.paginationdemo.data.network.RetrofitBuilder
import com.roshanadke.paginationdemo.data.repository.QuotesRepository
import com.roshanadke.paginationdemo.data.repository.QuotesRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MainActivityViewModelTest {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var repository: QuotesRepository
    private lateinit var apiService: QuotesApiService

    @Before
    fun setUp() {
        apiService = FakeQuotesService()
        repository = QuotesRepositoryImpl(
            apiService
        )
        viewModel = MainActivityViewModel(repository = repository)
    }

    @Test
    fun `Test get quotes`() = runBlocking {
        viewModel.getQuotes(1)
    }


}