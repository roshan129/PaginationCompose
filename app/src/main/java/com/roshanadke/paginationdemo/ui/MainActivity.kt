package com.roshanadke.paginationdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.roshanadke.paginationdemo.R
import com.roshanadke.paginationdemo.data.network.QuotesApiService
import com.roshanadke.paginationdemo.data.network.RetrofitBuilder
import com.roshanadke.paginationdemo.data.repository.QuotesRepository
import com.roshanadke.paginationdemo.data.repository.QuotesRepositoryImpl
import com.roshanadke.paginationdemo.ui.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViewModel()



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

