package com.rapidresponse.covid19

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rapidresponse.covid19.api.ApiError
import com.rapidresponse.covid19.data.Country
import com.rapidresponse.covid19.data.Summary
import com.rapidresponse.covid19.data.UIResponse
import com.rapidresponse.covid19.home.NovelCovidRepository
import kotlinx.coroutines.launch

class MainActivityViewModel
    (private val covidRepository: NovelCovidRepository): ViewModel() {

    private val summaryMutableLiveData = MutableLiveData<UIResponse<Summary>>()
    val summaryLiveData: LiveData<UIResponse<Summary>> = summaryMutableLiveData

    private val countriesMutableLiveData = MutableLiveData<UIResponse<List<Country>>>()
    val countriesLiveData: LiveData<UIResponse<List<Country>>> = countriesMutableLiveData

    fun start() {
        getSummary()
        getCountries()
    }

    fun getSummary(){
        summaryMutableLiveData.value = UIResponse.Loading
        viewModelScope.launch {
            val response = covidRepository.getSummary()
            response.error?.also {
                summaryMutableLiveData.value = UIResponse.Error(ApiError(code = it.code, message = it.message))
            }?: response.data?.let {
                summaryMutableLiveData.value = UIResponse.Data(it)
            }
        }
    }

    fun getCountries(){
        countriesMutableLiveData.value = UIResponse.Loading
        viewModelScope.launch {
            val response = covidRepository.getCountries()
            response.error?.also {
                countriesMutableLiveData.value = UIResponse.Error(ApiError(code = it.code, message = it.message))
            }?: response.data?.let {
                countriesMutableLiveData.value = UIResponse.Data(it)
            }
        }
    }


}