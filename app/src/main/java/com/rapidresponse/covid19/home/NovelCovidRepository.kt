package com.rapidresponse.covid19.home

import com.rapidresponse.covid19.api.ApiClient
import com.rapidresponse.covid19.api.ApiResponse
import com.rapidresponse.covid19.data.Country
import com.rapidresponse.covid19.data.Summary

class NovelCovidRepository(private val apiClient: ApiClient) {

    suspend fun getSummary(): ApiResponse<Summary>{
        return apiClient.geSummary()
    }

    suspend fun getCountries(): ApiResponse<List<Country>>{
        return apiClient.getCountries()
    }

}