package com.rapidresponse.covid19.data

import com.google.gson.annotations.SerializedName

data class Summary (
    @SerializedName("cases") val cases : Long,
    @SerializedName("deaths") val deaths : Long,
    @SerializedName("recovered") val recovered : Long,
    @SerializedName("updated") val updated : Long
)
