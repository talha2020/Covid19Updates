package com.rapidresponse.covid19.countries

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rapidresponse.covid19.GenericAdapter
import com.rapidresponse.covid19.R
import com.rapidresponse.covid19.data.Country
import com.squareup.picasso.Picasso

class CountriesListViewHolder(itemView: View,
                              val onItemClick: (Country) -> Unit): RecyclerView.ViewHolder(itemView),
    GenericAdapter.Binder<Country> {

    private var listItem = itemView.findViewById<LinearLayout>(R.id.countryListItem)
    private var countryFlagIv = itemView.findViewById<ImageView>(R.id.countryFlagIv)
    private var countryNameTv = itemView.findViewById<TextView>(R.id.countryNameTv)

    private var totalTv = itemView.findViewById<TextView>(R.id.totalTv)
    private var activeTv = itemView.findViewById<TextView>(R.id.activeTv)
    private var deathsTv = itemView.findViewById<TextView>(R.id.deathsTv)
    private var recoveredTv = itemView.findViewById<TextView>(R.id.recoveredTv)

    override fun bind(data: Country) {
        Picasso.get().load(data.countryInfo.flag).into(countryFlagIv)
        countryNameTv.text = data.country
        listItem.setOnClickListener { onItemClick(data) }

        totalTv.text = itemView.context.getString(R.string.total_num, data.formattedTotal)
        activeTv.text = itemView.context.getString(R.string.active_num, data.formattedActive)
        deathsTv.text = itemView.context.getString(R.string.deaths_num, data.formattedDeaths)
        recoveredTv.text = itemView.context.getString(R.string.recovered_num, data.formattedRecovered)
    }

}