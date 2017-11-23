// this is to allow method names to be with natural language
@file:Suppress("IllegalIdentifier")

package com.droidcba.kedditbysteps

import com.droidcba.kedditbysteps.api.*
import com.droidcba.kedditbysteps.features.news.NewsManager
import com.droidcba.kedditbysteps.util.MockedCall
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
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

    var apiMock = mock<NewsAPI>()

    @Before
    fun setup() {
        apiMock = mock<NewsAPI>()
    }

    @Test
    fun `Success - check response is not null`() = testBlocking {
        // prepare
        RedditNewsResponse(RedditDataResponse(listOf(), null, null)).mockApiCall()

        // call
        val newsManager = NewsManager(apiMock)

        val redditNews = newsManager.getNews("")

        // assert
        assertNotNull(redditNews)
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
        RedditNewsResponse(RedditDataResponse(listOf(newsResponse), null, null))
                .mockApiCall()

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
        val callMock = MockedCall<RedditNewsResponse>(exception = Throwable())
        whenever(apiMock.getNews(any(), any())).thenReturn(callMock)

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

    private fun RedditNewsResponse.mockApiCall() {
        val callMock = MockedCall<RedditNewsResponse>(this)
        whenever(apiMock.getNews(any(), any())).thenReturn(callMock)
    }
}