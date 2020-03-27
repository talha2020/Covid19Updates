package com.rapidresponse.covid19.data

import com.google.gson.annotations.SerializedName
import java.text.NumberFormat
import java.util.*

data class Summary (
    @SerializedName("cases") val cases : Long,
    @SerializedName("deaths") val deaths : Long,
    @SerializedName("recovered") val recovered : Long,
    @SerializedName("updated") val updated : Long
){
    val formattedCases: String
        get() = NumberFormat.getNumberInstance(Locale.getDefault()).format(cases)

    val formattedDeaths: String
        get() = NumberFormat.getNumberInstance(Locale.getDefault()).format(deaths)

    val formattedRecovered: String
        get() = NumberFormat.getNumberInstance(Locale.getDefault()).format(recovered)

}
