package com.rapidresponse.covid19

import android.app.Application
import com.facebook.stetho.Stetho

class MyApplication: Application()  {

    override fun onCreate() {
        super.onCreate()
        mContext = this
        Stetho.initializeWithDefaults(this)
    }

    companion object {
        private lateinit var mContext: MyApplication
        fun getContext(): MyApplication {
            return mContext
        }
    }

}