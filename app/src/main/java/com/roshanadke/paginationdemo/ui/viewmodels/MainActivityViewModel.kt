package com.roshanadke.paginationdemo.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.roshanadke.paginationdemo.data.dto.Results
import com.roshanadke.paginationdemo.data.repository.ListItem
import com.roshanadke.paginationdemo.data.repository.QuotesRepository
import com.roshanadke.paginationdemo.data.repository.Repository
import com.roshanadke.paginationdemo.paging.DefaultPaginator
import com.roshanadke.paginationdemo.paging.QuotesPaginator
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val quotesRepository: QuotesRepository
) : ViewModel() {


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
        quotesRepository.getQuotes(page, 10).onEach {
            Log.d("TAG", "getQuotes: result: list size:  ${it.results.size}")
        }.launchIn(viewModelScope)
    }

    var state by mutableStateOf(ScreenState())
    var quotesListState by mutableStateOf(QuotesScreenState())

    private val repository = Repository()

    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            repository.getItems(
                nextPage,
                10
            )
        },
        getNextKey = {
            state.page + 1
        },
        onError = {
            state = state.copy(error = it?.localizedMessage)
        },
        onSuccess = { items, newKey ->
            state = state.copy(
                items = state.items + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )

    private val quotesPaginator = QuotesPaginator(
        initialKey = quotesListState.page,
        onLoadUpdated = {
            quotesListState = quotesListState.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            quotesRepository.getPagingQuotes(
                nextPage,
                10
            )
        },
        getNextKey = {
            quotesListState.page + 1
        },
        onError = {
            quotesListState = quotesListState.copy(error = it?.localizedMessage)
        },
        onSuccess = { items, newKey ->
            quotesListState = quotesListState.copy(
                items = quotesListState.items + items.results,
                page = newKey,
                endReached = items.results.isEmpty()
            )
        }

    )

    init {
        //loadNextItem()
        loadQuotesPagingItems()
    }


    fun loadQuotesPagingItems() {
        viewModelScope.launch {
            quotesPaginator.loadNextItems()
        }
    }

    fun loadNextItem() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }


}

data class QuotesScreenState(
    val isLoading: Boolean = false,
    val items: List<Results> = emptyList(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0
)

data class ScreenState(
    val isLoading: Boolean = false,
    val items: List<ListItem> = emptyList(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0
)