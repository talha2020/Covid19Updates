package com.rapidresponse.covid19.countries

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.rapidresponse.covid19.*
import com.rapidresponse.covid19.data.Country
import com.rapidresponse.covid19.data.DataPointsWrapper
import com.rapidresponse.covid19.data.UIResponse
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_country_detail.*
import kotlinx.android.synthetic.main.content_country_detail.*
import kotlinx.android.synthetic.main.content_country_detail.progressBar
import kotlinx.android.synthetic.main.fragment_home.deathsTv
import kotlinx.android.synthetic.main.fragment_home.recoveredTv
import kotlinx.android.synthetic.main.fragment_home.totalCasesTv
import java.text.SimpleDateFormat
import java.util.*


class CountryDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CountryDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_detail)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProvider(this, CountryDetailViewModelFactory()).get(
            CountryDetailViewModel::class.java)

        val country = intent.getParcelableExtra<Country>(COUNTRY_KEY)
        title = country.country

        Picasso.get().load(country.countryInfo.flag).into(object : Target{
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                toolbar_layout.background = BitmapDrawable(resources, bitmap)
            }

        })

        viewModel.historicalDataLiveData.observe(this, Observer{ response ->
            when(response){
                is UIResponse.Loading -> {
                    progressBar.show()
                }
                is UIResponse.Data<DataPointsWrapper> -> {
                    progressBar.setGone()
                    setGraph(response.data)
                }
                is UIResponse.Error -> {
                    progressBar.setGone()
                    onError(response)
                }
            }
        })

        setSummaryView(country)

        viewModel.getHistoricalData(country.country)
    }

    private fun setSummaryView(country: Country) {
        totalCasesTv.text = country.formattedTotal
        deathsTv.text = country.formattedDeaths
        recoveredTv.text = country.formattedRecovered

        casesTodayTv.text = country.formattedCasesToday
        deathsTodayTv.text = country.formattedDeathsToday
        criticalTv.text = country.formattedCritical
    }

    private fun setGraph(data: DataPointsWrapper) {
        setTotalCasesGraph(data.totalCases)
        setTotalDeathsGraph(data.deaths)
    }

    //TODO: Refactor them to use same code
    private fun setTotalCasesGraph(data: List<DataPoint>){
        val formatter = SimpleDateFormat("d MMM", Locale.ENGLISH)

        val series = LineGraphSeries(
            data.toTypedArray()
        )

        graphTotalCases.addSeries(series)
        graphTotalCases.title = "Total cases"
        graphTotalCases.gridLabelRenderer.labelFormatter = DateAsXAxisLabelFormatter(this, formatter)
    }

    private fun setTotalDeathsGraph(data: List<DataPoint>){
        val formatter = SimpleDateFormat("d MMM", Locale.ENGLISH)

        val series = LineGraphSeries(
            data.toTypedArray()
        )

        graphTotalDeaths.addSeries(series)
        graphTotalDeaths.title = "Total deaths"
        graphTotalDeaths.gridLabelRenderer.labelFormatter = DateAsXAxisLabelFormatter(this, formatter)
    }

}
