// this is to allow method names to be with natural language
@file:Suppress("IllegalIdentifier")

package com.droidcba.kedditbysteps

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.droidcba.kedditbysteps.api.*
import com.droidcba.kedditbysteps.features.news.NewsState
import com.droidcba.kedditbysteps.features.news.NewsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.*
import kotlinx.coroutines.experimental.Deferred
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.*

/**
 * Unit Tests for NewsViewModel
 *
 * @author juancho.
 */
class NewsViewModelTest {

    /**
     * This rule will allow LiveDataMutable to propagate the postValue so we can use it.
     */
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    /**
     * CoroutineContext that will trigger all jobs executions in the same thread.
     */
    private val coroutineContext = TestDirectContext()

    private var apiMock = mockk<NewsAPI>()
    private val deferredMock = mockk<Deferred<RedditNewsResponse>>()
    private var newsViewModel = NewsViewModel(apiMock, coroutineContext)

    @Before
    fun setup() {
        coEvery { apiMock.getNews(any(), any()) } returns deferredMock
    }

    @Test
    fun `Success - check response is not null`() {
        // prepare
        val news = RedditNewsResponse(RedditDataResponse(listOf(), null, null))
        coEvery { deferredMock.await() } returns news

        // call
        newsViewModel.fetchNews("")

        // assert
        val actualValue = newsViewModel.newsState.value
        assertNotNull(actualValue)
        assertTrue(actualValue is NewsState)
    }

    @Test
    fun `Success - checks received one news`() {
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
        coEvery { deferredMock.await() } returns news

        // call
        newsViewModel.fetchNews("")

        // assert
        val actualValue = newsViewModel.newsState.value
        assertNotNull(actualValue)
        assertTrue(actualValue is NewsState.Success)

        val state = actualValue as NewsState.Success
        assertEquals(newsData.author, state.redditNews.news[0].author)
        assertEquals(newsData.title, state.redditNews.news[0].title)
    }

    @Test
    fun `Error - Exception received from service call`() {
        // prepare
        coEvery { apiMock.getNews(any(), any()) } throws Throwable()

        // call
        newsViewModel.fetchNews("")

        // assert
        val actualValue = newsViewModel.newsState.value
        assertNotNull(actualValue)
        assertTrue(actualValue is NewsState.Error)
    }
}