package com.droidcba.kedditbysteps.api

import kotlinx.coroutines.experimental.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface  RedditApi {
    @GET("/top.json")
    fun getTop(@Query("after") after: String,
               @Query("limit") limit: String): Call<RedditNewsResponse>

    @GET("/top.json")
    fun getDeferredTop(@Query("after") after: String,
               @Query("limit") limit: String): Deferred<RedditNewsResponse>
}