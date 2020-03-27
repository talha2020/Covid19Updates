package com.rapidresponse.covid19.countries

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.rapidresponse.covid19.COUNTRY_KEY
import com.rapidresponse.covid19.R
import com.rapidresponse.covid19.data.Country
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_country_detail.*
import kotlinx.android.synthetic.main.fragment_home.*

class CountryDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_detail)
        setSupportActionBar(toolbar)

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
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        setSummaryView(country)
    }

    private fun setSummaryView(country: Country) {
        totalCasesTv.text = country.formattedTotal
        deathsTv.text = country.formattedDeaths
        recoveredTv.text = country.formattedRecovered
    }

}
