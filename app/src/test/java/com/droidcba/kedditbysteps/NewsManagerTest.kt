package com.droidcba.kedditbysteps

import com.droidcba.kedditbysteps.api.*
import com.droidcba.kedditbysteps.commons.RedditNews
import com.droidcba.kedditbysteps.features.news.NewsManager
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import retrofit2.Call
import retrofit2.Response
import rx.observers.TestSubscriber
import java.util.*

/**
 * Unit Tests for NewsManager
 *
 * @author juancho.
 */
class NewsManagerTest {

    var testSub = TestSubscriber<RedditNews>()
    var apiMock = mock<NewsAPI>()
    var callMock = mock<Call<RedditNewsResponse>>()

    @Before
    fun setup() {
        testSub = TestSubscriber<RedditNews>()
        apiMock = mock<NewsAPI>()
        callMock = mock<Call<RedditNewsResponse>>()
        `when`(apiMock.getNews(anyString(), anyString())).thenReturn(callMock)
    }

    @Test
    fun testSuccess_basic() {
        // prepare
        val redditNewsResponse = RedditNewsResponse(RedditDataResponse(listOf(), null, null))
        val response = Response.success(redditNewsResponse)

        `when`(callMock.execute()).thenReturn(response)

        // call
        val newsManager = NewsManager(apiMock)
        newsManager.getNews("").subscribe(testSub)

        // assert
        testSub.assertNoErrors()
        testSub.assertValueCount(1)
        testSub.assertCompleted()
    }

    @Test
    fun testSuccess_checkOneNews() {
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
        val redditNewsResponse = RedditNewsResponse(RedditDataResponse(listOf(newsResponse), null, null))
        val response = Response.success(redditNewsResponse)

        `when`(callMock.execute()).thenReturn(response)

        // call
        val newsManager = NewsManager(apiMock)
        newsManager.getNews("").subscribe(testSub)

        // assert
        testSub.assertNoErrors()
        testSub.assertValueCount(1)
        testSub.assertCompleted()

        assert(testSub.onNextEvents[0].news[0].author == newsData.author)
        assert(testSub.onNextEvents[0].news[0].title == newsData.title)
    }

    @Test
    fun testError() {
        // prepare
        val responseError = Response.error<RedditNewsResponse>(500,
                ResponseBody.create(MediaType.parse("application/json"), ""))

        `when`(callMock.execute()).thenReturn(responseError)

        // call
        val newsManager = NewsManager(apiMock)
        newsManager.getNews("").subscribe(testSub)

        // assert
        assert(testSub.onErrorEvents.size == 1)
    }
}

inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)