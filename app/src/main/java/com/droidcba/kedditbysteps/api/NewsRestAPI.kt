package com.droidcba.kedditbysteps.api

import retrofit2.Call
import javax.inject.Inject

class NewsRestAPI @Inject constructor(private val redditApi: RedditApi) : NewsAPI {

    override suspend fun getNews(after: String, limit: String): RedditNewsResponse {
        return redditApi.getDeferredTop(after, limit).await()
    }

    override fun getNewsOldApi(after: String, limit: String): Call<RedditNewsResponse> {
        return redditApi.getTop(after, limit)
    }
}