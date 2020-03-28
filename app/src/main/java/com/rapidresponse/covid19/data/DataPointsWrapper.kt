package com.rapidresponse.covid19.data

import com.jjoe64.graphview.series.DataPoint

data class DataPointsWrapper (
    val totalCases: List<DataPoint>,
    val deaths: List<DataPoint>
)