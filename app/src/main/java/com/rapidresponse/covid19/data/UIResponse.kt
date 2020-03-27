package com.rapidresponse.covid19.data

import com.rapidresponse.covid19.api.ApiError

sealed class UIResponse<out T> {
    data class Error(val error: ApiError) : UIResponse<Nothing>()
    data class Data<T>(val data: T) : UIResponse<T>()
    object Loading : UIResponse<Nothing>()
}