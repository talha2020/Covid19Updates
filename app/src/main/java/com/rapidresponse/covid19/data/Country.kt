package com.rapidresponse.covid19.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.text.NumberFormat
import java.util.*

@Parcelize
data class Country(
    @SerializedName("country") val country : String,
    @SerializedName("countryInfo") val countryInfo : CountryInfo,
    @SerializedName("cases") val cases : Long,
    @SerializedName("todayCases") val todayCases : Long,
    @SerializedName("deaths") val deaths : Long,
    @SerializedName("todayDeaths") val todayDeaths : Long,
    @SerializedName("recovered") val recovered : Long,
    @SerializedName("active") val active : Long,
    @SerializedName("critical") val critical : Long,
    @SerializedName("casesPerOneMillion") val casesPerOneMillion : Double,
    @SerializedName("deathsPerOneMillion") val deathsPerOneMillion : Double
): Parcelable {
    val formattedTotal: String
        get() = getFormattedNumber(cases)

    val formattedActive: String
        get() = getFormattedNumber(active)

    val formattedDeaths: String
        get() = getFormattedNumber(deaths)

    val formattedRecovered: String
        get() = getFormattedNumber(recovered)

    val formattedCasesToday: String
        get() = getFormattedNumber(todayCases)

    val formattedDeathsToday: String
        get() = getFormattedNumber(todayDeaths)

    val formattedCritical: String
        get() = getFormattedNumber(critical)

    private fun getFormattedNumber(number: Long): String {
        return NumberFormat.getNumberInstance(Locale.getDefault()).format(number)
    }
}

@Parcelize
data class CountryInfo (
    @SerializedName("_id") val _id : Int,
    @SerializedName("lat") val lat : Double,
    @SerializedName("long") val long : Double,
    @SerializedName("flag") val flag : String,
    @SerializedName("iso3") val iso3 : String,
    @SerializedName("iso2") val iso2 : String
): Parcelable
