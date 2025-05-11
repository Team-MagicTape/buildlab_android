package com.narsha.buildlab

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context


class BuildLabApplication : Application() {

    override fun onCreate() {
        super.onCreate()
//        RetrofitClient.init(this)
        context = applicationContext
    }


    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        fun getContext() = context
    }
}