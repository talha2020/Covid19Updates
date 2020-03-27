package com.rapidresponse.covid19.api

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.rapidresponse.covid19.BuildConfig
import com.rapidresponse.covid19.data.Summary
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import okhttp3.logging.HttpLoggingInterceptor
import java.net.UnknownHostException

class ApiClient {
    private val client = HttpClient(OkHttp) {
        engine {
            if (BuildConfig.DEBUG){
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY

                addInterceptor(logging)
                addNetworkInterceptor(StethoInterceptor())
            }
        }
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
        expectSuccess = true
    }

    suspend fun geSummary(): ApiResponse<Summary>{
        val url = ApiEndPoints.SUMMARY
        return try {
            val response = client.get<Summary>(url)
            ApiResponse(data = response)
        } catch (ex: Exception){
            processException(ex)
        }
    }

    private fun <T: Any> processException(ex: Exception): ApiResponse<T>{
        ex.printStackTrace()
        return if (ex is UnknownHostException)
            ApiResponse(error = ApiError(code = ErrorCodes.NO_INTERNET.value, message = ex.message))
        else
            ApiResponse(error = ApiError(code = ErrorCodes.UNKNOWN.value, message = ex.message))
    }
}