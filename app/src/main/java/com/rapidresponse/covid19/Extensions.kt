package com.rapidresponse.covid19

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.rapidresponse.covid19.api.ErrorCodes
import com.rapidresponse.covid19.data.UIResponse

const val COUNTRY_KEY = "country_key"

fun View.show(){
    this.visibility = View.VISIBLE
}

fun View.setInvisible(){
    this.visibility = View.INVISIBLE
}

fun View.setGone() {
    this.visibility = View.GONE
}

fun Activity.showError(errorMessage: String) {
    showError(this, errorMessage)
}

fun Fragment.showError(errorMessage: String){
    context?.let { showError(it,errorMessage) }
}

private fun showError(context: Context, errorMessage: String) {
    with(context as? AppCompatActivity) {
        val viewGroup =
            (this?.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
        Snackbar.make(viewGroup, errorMessage, Snackbar.LENGTH_LONG).show()
    }
}

fun Activity.onError(response: UIResponse.Error) {
    onError(this, response)
}

fun Fragment.onError(response: UIResponse.Error){
    context?.let { onError(it,response) }
}

private fun onError(context: Context, response: UIResponse.Error) {
    val code = response.error.code.toString()
    when {
        code.contentEquals(ErrorCodes.NO_INTERNET.value.toString()) -> showError(context, context.getString(R.string.request_failed_error))
        else -> response.error.message?.let { showError(context, it) }
    }
}

