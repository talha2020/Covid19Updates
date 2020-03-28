package com.rapidresponse.covid19.countries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rapidresponse.covid19.api.ApiClient
import com.rapidresponse.covid19.home.NovelCovidRepository

@Suppress("UNCHECKED_CAST")
class CountryDetailViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryDetailViewModel::class.java)) {
            val apiClient = ApiClient()
            return CountryDetailViewModel(
                covidRepository = NovelCovidRepository(
                    apiClient = apiClient
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}