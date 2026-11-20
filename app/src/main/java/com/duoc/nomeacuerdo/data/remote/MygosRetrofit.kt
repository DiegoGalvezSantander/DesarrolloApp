package com.duoc.nomeacuerdo.data.remote

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object MygosRetrofit {
    private const val BASE_URL = "https://x8ki-letl-twmt.n7.xano.io/api:Ml8tYmND/"

    private lateinit var cache: Cache

    fun init(context: Context) {
        val cacheSize = 10 * 1024 * 1024
        val cacheDir = File(context.cacheDir, "http_cache")
        cache = Cache(cacheDir, cacheSize.toLong())
    }

    fun clearCache() {
        if (::cache.isInitialized) {
            cache.evictAll()
        }
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request = chain.request()
                val response = chain.proceed(request)

                if (request.method == "GET") {
                    response.newBuilder()
                        .header("Cache-Control", "public, max-age=300")
                        .build()
                } else {
                    response
                }
            }
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
    }

    val api: MygosApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MygosApi::class.java)
    }
}