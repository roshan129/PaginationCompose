package com.roshanadke.paginationdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roshanadke.paginationdemo.R
import com.roshanadke.paginationdemo.data.network.RetrofitBuilder
import com.roshanadke.paginationdemo.data.repository.QuotesRepositoryImpl
import com.roshanadke.paginationdemo.ui.viewmodels.MainActivityViewModel

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpViewModel()

        setContent {

            //val state = viewModel.state
            val state = viewModel.quotesListState

            Log.d("TAG", "onCreate: ${state.items}")

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.items.size) { index ->
                    val item = state.items[index]

                    Log.d("TAG", "onCreate: index: $index ")
                    Log.d("TAG", "onCreate: endReached: ${state.endReached} ")
                    Log.d("TAG", "onCreate: isLoading: ${state.isLoading} ")
                    if(index >= state.items.size - 3 && !state.endReached && !state.isLoading) {
                        viewModel.loadQuotesPagingItems()
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = item.content,
                            fontSize = 20.sp,
                            color = Color.Black

                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = item.author)

                    }
                }
                item {
                    if(state.isLoading) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {

                            CircularProgressIndicator()
                        }
                    }
                }
            }

        }


    }


    private fun setUpViewModel() {
        val repository = QuotesRepositoryImpl(RetrofitBuilder.apiService)

        //first way
        //val viewModelFactory: ViewModelProvider.Factory = MainActivityViewModelFactory(repository)

        //second way
        val viewModelFactory = MainActivityViewModel.provideFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)

        viewModel.getQuotes(1)
    }
}

class MainActivityViewModelFactory(private val repository: QuotesRepositoryImpl) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

