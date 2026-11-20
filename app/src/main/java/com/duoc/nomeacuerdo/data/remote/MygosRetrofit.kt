package com.duoc.nomeacuerdo.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object MygosRetrofit {
    private const val BASE_URL = "https://x8ki-letl-twmt.n7.xano.io/api:Ml8tYmND/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val request = chain.request()
            var response = chain.proceed(request)
            var tryCount = 0
            while (!response.isSuccessful && response.code == 429 && tryCount < 3) {
                tryCount++
                Thread.sleep(3000)
                response.close()
                response = chain.proceed(request)
            }
            response
        }
        .build()

    val api: MygosApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MygosApi::class.java)
    }
}