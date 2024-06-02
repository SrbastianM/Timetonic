package com.srbastian.timetonic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    private val url = "https://api.timetonic.com/"

    val instance: RetrofitClient by lazy {
        Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build()
    }

    val service : TimetonicService by lazy {
        instance.create(TimeTonic::class.java)
    }
}