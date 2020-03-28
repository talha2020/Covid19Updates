package com.rapidresponse.covid19.home

import com.prof.rssparser.Article
import com.rapidresponse.covid19.api.ApiClient
import com.rapidresponse.covid19.api.ApiResponse
import com.rapidresponse.covid19.data.Country
import com.rapidresponse.covid19.data.HistoricalData
import com.rapidresponse.covid19.data.Summary

class NovelCovidRepository(private val apiClient: ApiClient) {

    suspend fun getSummary(): ApiResponse<Summary>{
        return apiClient.geSummary()
    }

    suspend fun getCountries(sort: String = "cases"): ApiResponse<List<Country>>{
        return apiClient.getCountries(sort)
    }

    suspend fun getNews(): ApiResponse<List<Article>> {
         return apiClient.getNews()
    }

    suspend fun getHistoricalData(country: String): ApiResponse<HistoricalData> {
        return apiClient.getHistoricalData(country)
    }

}