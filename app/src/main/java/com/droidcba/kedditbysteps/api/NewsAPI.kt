package com.droidcba.kedditbysteps.api

import rx.Observable

/**
 * News API
 *
 * @author juancho.
 */
interface NewsAPI {
    fun getNews(after: String, limit: String): Observable<RedditNewsResponse>
}