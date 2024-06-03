package com.srbastian.timetonic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val url = "https://timetonic.com/live/"

    val instance: Retrofit by lazy {
        Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Change the name of the class to TimetonicService -> is more legible
    val service: TimetonicInterface by lazy {
        instance.create(TimetonicInterface::class.java)
    }
}
