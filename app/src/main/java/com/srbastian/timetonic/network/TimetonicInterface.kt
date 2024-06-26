package com.srbastian.timetonic.network

import com.srbastian.timetonic.data.book.BooksResponse
import com.srbastian.timetonic.data.login.AppKeyResponse
import com.srbastian.timetonic.data.login.OauhtKeyResponse
import com.srbastian.timetonic.data.login.SessKeyResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

interface TimetonicInterface {
    // Login
    @POST("api.php")
    suspend fun createAppKey(
        @Query("version") version: String = "6.49",
        @Query("req") req: String = "createAppkey",
        @Query("appname") appName: String = "Timetonic",
    ): Response<AppKeyResponse>

    @POST("api.php")
    suspend fun createOauthkey(
        @Query("version") version: String = "6.49",
        @Query("req") req: String = "createOauthkey",
        @Query("login") login: String,
        @Query("pwd") pwd: String,
        @Query("appkey") appkey: String,
    ): Response<OauhtKeyResponse>

    @POST("api.php")
    suspend fun createSesskey(
        @Query("version") version: String = "6.49",
        @Query("req") req: String = "createSesskey",
        @Query("o_u") o_u: String,
        @Query("id") u_c: String,
        @Query("oauthkey") oauthkey: String,
    ): Response<SessKeyResponse>

    // Get associated books
    @FormUrlEncoded
    @POST("api.php")
    suspend fun getAllBooks(
        @Field("version") version: String = "6.49",
        @Field("req") req: String = "getAllBooks",
        @Field("u_c") u_c: String,
        @Field("o_u") o_u: String,
        @Field("sesskey") sessKey: String,
    ): Response<BooksResponse>
}
