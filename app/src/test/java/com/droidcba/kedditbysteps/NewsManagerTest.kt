// this is to allow method names to be with natural language
@file:Suppress("IllegalIdentifier")

package com.droidcba.kedditbysteps

import com.droidcba.kedditbysteps.api.*
import com.droidcba.kedditbysteps.features.news.NewsManager
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

/**
 * Unit Tests for NewsManager
 *
 * @author juancho.
 */
class NewsManagerTest {

    private var apiMock = mock<NewsAPI>()

    @Test
    fun `Success - check response is not null`() = testBlocking {
        // prepare
        val news = RedditNewsResponse(RedditDataResponse(listOf(), null, null))
        apiMock = mock {
            onBlocking { getNews(any(), any()) } doReturn news
        }

        // call
        val newsManager = NewsManager(apiMock)

        val redditNews = newsManager.getNews("")

        // assert
        assertNotNull(redditNews)
        assert(redditNews.news.count() > 0)
    }

    @Test
    fun `Success - checks received one news`() = testBlocking {
        // prepare
        val newsData = RedditNewsDataResponse(
                "author",
                "title",
                10,
                Date().time,
                "thumbnail",
                "url"
        )
        val newsResponse = RedditChildrenResponse(newsData)
        val news = RedditNewsResponse(RedditDataResponse(listOf(newsResponse), null, null))
        apiMock = mock {
            onBlocking { getNews(any(), any()) } doReturn news
        }

        // call
        val newsManager = NewsManager(apiMock)

        val redditNews = newsManager.getNews("")

        // assert
        assertNotNull(redditNews)
        assertEquals(newsData.author, redditNews.news[0].author)
        assertEquals(newsData.title, redditNews.news[0].title)
    }

    @Test
    fun `Error - Exception received from service call`() {
        // prepare
        apiMock = mock {
            onBlocking { getNews(any(), any()) } doAnswer { throw Throwable() }
        }

        // call
        val newsManager = NewsManager(apiMock)
        assertFailsWith<Throwable> {
            runBlocking {
                newsManager.getNews("")
            }
        }
    }

    private fun testBlocking(block: suspend CoroutineScope.() -> Unit) {
        runBlocking(Unconfined, block)
    }
}