package com.droidcba.kedditbysteps.api

import retrofit2.Call

interface NewsAPI {
    fun getNewsOldApi(after: String, limit: String): Call<RedditNewsResponse>
    suspend fun getNews(after: String, limit: String): RedditNewsResponse
}