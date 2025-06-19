package com.narsha.buildlab_android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.narsha.datastore.tokenManager.TokenManager
import com.narsha.network.core.remote.RetrofitClient


class BuildLabApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        RetrofitClient.init(this)
        TokenManager.init(applicationContext)
        context = applicationContext
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        fun getContext() = context
    }

}