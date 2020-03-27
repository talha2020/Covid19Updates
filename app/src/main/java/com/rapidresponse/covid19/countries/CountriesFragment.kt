package com.rapidresponse.covid19.countries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rapidresponse.covid19.*
import com.rapidresponse.covid19.data.Country
import com.rapidresponse.covid19.data.UIResponse
import kotlinx.android.synthetic.main.fragment_countries.*
import kotlinx.android.synthetic.main.fragment_home.progressBar

class CountriesFragment : Fragment() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = activity?.run {
            ViewModelProvider(this, MainViewModelFactory())
                .get(MainActivityViewModel::class.java)
        } ?: throw Exception(
            getString(R.string.invalid_activity)
        )

        return inflater.inflate(R.layout.fragment_countries, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.countriesLiveData.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is UIResponse.Loading -> {
                    progressBar.show()
                }
                is UIResponse.Data<List<Country>> -> {
                    progressBar.setGone()
                    showCountriesList(response.data)
                }
                is UIResponse.Error -> {
                    progressBar.setGone()
                    onError(response)
                }
            }
        })
    }

    private fun showCountriesList(countries: List<Country>) {

        val adapter = object : GenericAdapter<Country>(countries) {
            override fun getLayoutId(position: Int, obj: Country): Int {
                return R.layout.country_list_item
            }

            override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
                return CountriesListViewHolder(view, onItemClick = {
                    launchCountryDetailActivity(it)
                })
            }
        }
        countriesRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        countriesRv.adapter = adapter

    }

    private fun launchCountryDetailActivity(country: Country) {
        showError(country.country)
    }

}