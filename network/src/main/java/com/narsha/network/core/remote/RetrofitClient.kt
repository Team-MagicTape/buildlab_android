package com.narsha.network.core.remote

import android.content.Context
import com.google.gson.GsonBuilder
import com.narsha.network.BuildLabUrl
import com.narsha.network.auth.api.AuthApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun init(context: Context) {
        val gson = GsonBuilder().setLenient().create()

        val client = OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(RequestInterceptor(NetworkUtil(context)))
            .addInterceptor(ResponseInterceptor())
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BuildLabUrl.BASE_URL + "/")
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun getRetrofit(): Retrofit {
        return retrofit ?: throw IllegalStateException("RetrofitClient is not initialized")
    }

    val authService: AuthApi by lazy {
        getRetrofit().create(AuthApi::class.java)
    }

}

