package com.roshanadke.paginationdemo.ui.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.roshanadke.paginationdemo.data.repository.QuotesRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivityViewModel(
    private val repository: QuotesRepository
): ViewModel() {

    val pagingQuotesList = repository.getPagerQuotes().cachedIn(viewModelScope)

    companion object {
        fun provideFactory(repository: QuotesRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
                        @Suppress("UNCHECKED_CAST")
                        return MainActivityViewModel(repository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }

    fun getQuotes(page: Int) {
        repository.getQuotes(page, 10).onEach {
            Log.d("TAG", "getQuotes: result: list size:  ${it.results.size}")
        }.launchIn(viewModelScope)
    }

}