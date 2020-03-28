package com.rapidresponse.covid19.countries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjoe64.graphview.series.DataPoint
import com.rapidresponse.covid19.api.ApiError
import com.rapidresponse.covid19.data.DataPointsWrapper
import com.rapidresponse.covid19.data.HistoricalData
import com.rapidresponse.covid19.data.UIResponse
import com.rapidresponse.covid19.home.NovelCovidRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CountryDetailViewModel
    (private val covidRepository: NovelCovidRepository): ViewModel() {

    private val historicalDataMutableLiveData = MutableLiveData<UIResponse<DataPointsWrapper>>()
    val historicalDataLiveData: LiveData<UIResponse<DataPointsWrapper>> = historicalDataMutableLiveData

    fun getHistoricalData(country: String){
        viewModelScope.launch {
            val response = covidRepository.getHistoricalData(country)
            response.error?.also {
                historicalDataMutableLiveData.value = UIResponse.Error(ApiError(code = it.code, message = it.message))
            }?: response.data?.let {
                historicalDataMutableLiveData.value = UIResponse.Data(
                    DataPointsWrapper(calculateCasesDataPoints(it),calculateDeathsDataPoints(it)))
            }
        }
    }

    private fun calculateCasesDataPoints(historicalData: HistoricalData): List<DataPoint> {
        return getDataPoints(historicalData.timeline.cases)
    }

    private fun calculateDeathsDataPoints(historicalData: HistoricalData): List<DataPoint> {
        return getDataPoints(historicalData.timeline.deaths)
    }

    private fun getDataPoints(timelineMap: HashMap<String, Int>): List<DataPoint> {
        val formatter = SimpleDateFormat("M/d/yy", Locale.ENGLISH)

        val list: MutableList<DataPoint> = mutableListOf()
        for((key, value) in timelineMap){
            list.add(DataPoint(formatter.parse(key), value.toDouble()))
        }

        list.sortBy { it.x }
        return list
    }

}