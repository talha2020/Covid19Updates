package com.rapidresponse.covid19.api

data class ApiResponse<T>(
    val data: T? = null,
    val error: ApiError? = null
)