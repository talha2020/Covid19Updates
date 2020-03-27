package com.rapidresponse.covid19.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.rapidresponse.covid19.MainActivityViewModel
import com.rapidresponse.covid19.MainViewModelFactory
import com.rapidresponse.covid19.R

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

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }
}