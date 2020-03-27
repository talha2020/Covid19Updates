package com.rapidresponse.covid19.api

data class ApiError(
    val code: Int?,
    val message: String? = "Unknown error occurred"
)