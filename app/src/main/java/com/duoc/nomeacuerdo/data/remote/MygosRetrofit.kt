package com.duoc.nomeacuerdo.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MygosRetrofit {
    private const val BASE_URL = "https://x8ki-letl-twmt.n7.xano.io/api:Ml8tYmND/"

    val api: MygosApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MygosApi::class.java)
    }
}