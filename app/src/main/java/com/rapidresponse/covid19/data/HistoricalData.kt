package com.rapidresponse.covid19.data

import com.google.gson.annotations.SerializedName

data class HistoricalData (
    @SerializedName("country") val country : String,
    @SerializedName("timeline") val timeline : Timeline
)

data class Timeline (
    @SerializedName("cases") val cases : HashMap<String, Int>,
    @SerializedName("deaths") val deaths : HashMap<String, Int>
)
