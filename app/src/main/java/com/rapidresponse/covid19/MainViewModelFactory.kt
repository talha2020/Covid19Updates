package com.rapidresponse.covid19

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rapidresponse.covid19.api.ApiClient
import com.rapidresponse.covid19.home.NovelCovidRepository

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            val apiClient = ApiClient()
            return MainActivityViewModel(
                covidRepository = NovelCovidRepository(
                    apiClient = apiClient
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}