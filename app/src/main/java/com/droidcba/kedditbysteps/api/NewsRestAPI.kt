package com.droidcba.kedditbysteps.api

import rx.Observable
import javax.inject.Inject

class NewsRestAPI @Inject constructor(private val redditApi: RedditApi) : NewsAPI {

    override fun getNews(after: String, limit: String): Observable<RedditNewsResponse> {
        return redditApi.getTop(after, limit)
    }
}