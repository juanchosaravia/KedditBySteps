package com.droidcba.kedditbysteps.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface  RedditApi {
    @GET("/top.json")
    fun getTop(@Query("after") after: String,
               @Query("limit") limit: String): Call<RedditNewsResponse>;
}