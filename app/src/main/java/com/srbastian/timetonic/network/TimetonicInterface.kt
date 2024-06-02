package com.srbastian.timetonic.network

import com.srbastian.timetonic.data.AppKeyRequest
import com.srbastian.timetonic.data.OauthKeyRequest
import com.srbastian.timetonic.data.SessKeyRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface TimetonicInterface {

    @POST("createAppkey")
    fun createAppKey(@Body request: AppKeyRequest): Call<AppKeyRequest>

    @POST("createOauthkey")
    fun createOauthkey(@Body request: OauthKeyRequest): Call<OauthKeyRequest>

    @POST("createSesskey")
    fun createSesskey(@Body request: SessKeyRequest): Call<SessKeyRequest>
}