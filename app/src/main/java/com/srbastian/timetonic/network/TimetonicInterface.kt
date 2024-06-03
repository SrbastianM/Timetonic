package com.srbastian.timetonic.network

import com.srbastian.timetonic.data.AppKeyResponse
import com.srbastian.timetonic.data.OauhtKeyResponse
import com.srbastian.timetonic.data.SessKeyResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface TimetonicInterface {
    // Login
    @POST("api.php")
    suspend fun createAppKey(
        @Query("version") version: String = "1.47",
        @Query("req") req: String = "createAppkey",
        @Query("appname") appName: String = "Timetonic",
    ): Response<AppKeyResponse>

    @POST("api.php")
    suspend fun createOauthkey(
        @Query("version") version: String = "1.47",
        @Query("req") req: String = "createOauthkey",
        @Query("login") login: String,
        @Query("pwd") pwd: String,
        @Query("appkey") appkey: String,
    ): Response<OauhtKeyResponse>

    @POST("api.php")
    suspend fun createSesskey(
        @Query("version") version: String = "1.47",
        @Query("req") req: String = "createSesskey",
        @Query("o_u") o_u: String,
        @Query("oauthkey") oauthkey: String,
    ): Response<SessKeyResponse>

    // Get associated books
}
