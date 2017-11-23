package com.droidcba.kedditbysteps.features.news

import com.droidcba.kedditbysteps.api.NewsAPI
import com.droidcba.kedditbysteps.api.RedditNewsResponse
import com.droidcba.kedditbysteps.commons.RedditNews
import com.droidcba.kedditbysteps.commons.RedditNewsItem
import ru.gildor.coroutines.retrofit.Result
import ru.gildor.coroutines.retrofit.awaitResult
import javax.inject.Inject
import javax.inject.Singleton

/**
 * News Manager allows you to request news from Reddit API.
 *
 * @author juancho
 */
@Singleton
class NewsManager @Inject constructor(private val api: NewsAPI) {

    /**
     *
     * Returns Reddit News paginated by the given limit.
     *
     * @param after indicates the next page to navigate.
     * @param limit the number of news to request.
     */
    suspend fun getNews(after: String, limit: String = "10"): RedditNews {
        val result = api.getNews(after, limit).awaitResult()
        return when (result) {
            is Result.Ok -> process(result.value)
            is Result.Error -> throw Throwable("HTTP error: ${result.response.message()}")
            is Result.Exception -> throw result.exception
            else -> {
                throw Throwable("Something went wrong, please try again later.")
            }
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