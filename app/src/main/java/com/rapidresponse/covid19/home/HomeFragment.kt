package com.rapidresponse.covid19.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rapidresponse.covid19.*
import com.rapidresponse.covid19.data.Summary
import com.rapidresponse.covid19.data.UIResponse
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.NumberFormat
import java.util.*

class HomeFragment : Fragment() {

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

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.summaryLiveData.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is UIResponse.Loading -> {
                    progressBar.show()
                }
                is UIResponse.Data<Summary> -> {
                    progressBar.setGone()
                    setSummaryView(response.data)
                }
                is UIResponse.Error -> {
                    progressBar.setGone()
                    onError(response)
                }
            }
        })
    }

    private fun setSummaryView(summary: Summary) {
        NumberFormat.getNumberInstance(Locale.getDefault()).format(summary.cases)
        totalCasesTv.text = summary.formattedCases
        deathsTv.text = summary.formattedDeaths
        recoveredTv.text = summary.formattedRecovered
    }
}