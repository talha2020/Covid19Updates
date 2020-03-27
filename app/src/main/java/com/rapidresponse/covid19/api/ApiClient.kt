package com.rapidresponse.covid19.api

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.prof.rssparser.Article
import com.prof.rssparser.Parser
import com.rapidresponse.covid19.BuildConfig
import com.rapidresponse.covid19.data.Country
import com.rapidresponse.covid19.data.Summary
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import io.ktor.client.request.parameter
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
        expectSuccess = false
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

    suspend fun getCountries(sort: String = "cases"): ApiResponse<List<Country>>{
        val url = ApiEndPoints.COUNTRIES
        return try {
            val response = client.get<List<Country>>(url){
                parameter("sort", sort)
            }
            ApiResponse(data = response)
        } catch (ex: Exception){
            processException(ex)
        }
    }

    suspend fun getNews(): ApiResponse<List<Article>> {
        val url = ApiEndPoints.WHO_RSS
        val parser = Parser()
        return try {
            val channel = parser.getChannel(url)
            ApiResponse(data = channel.articles)
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