package com.droidcba.kedditbysteps.features.news

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.droidcba.kedditbysteps.api.NewsAPI
import com.droidcba.kedditbysteps.api.RedditNewsResponse
import com.droidcba.kedditbysteps.commons.RedditNews
import com.droidcba.kedditbysteps.commons.RedditNewsItem
import com.droidcba.kedditbysteps.commons.extensions.runAsync
import javax.inject.Inject

/**
 * News Manager allows you to request news from Reddit API.
 *
 * @author juancho
 */
class NewsManager @Inject constructor(private val api: NewsAPI) : ViewModel() {

    val newsState: MutableLiveData<NewsState> = MutableLiveData()

    fun fetchNews(after: String, limit: String = "10") = runAsync {
        try {
            val result = api.getNews(after, limit).await()
            val news = process(result)
            newsState.postValue(NewsState.Success(news))
        } catch (e: Throwable) {
            newsState.postValue(NewsState.Error(e.message))
        }
    }

    private fun process(response: RedditNewsResponse): RedditNews {
        val dataResponse = response.data
        val news = dataResponse.children.map {
            val item = it.data
            RedditNewsItem(item.author, item.title, item.num_comments,
                    item.created, item.thumbnail, item.url)
        }
        return RedditNews(
                dataResponse.after.orEmpty(),
                dataResponse.before.orEmpty(),
                news)
    }
}