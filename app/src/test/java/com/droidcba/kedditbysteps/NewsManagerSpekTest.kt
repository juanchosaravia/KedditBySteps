package com.droidcba.kedditbysteps

import com.droidcba.kedditbysteps.api.*
import com.droidcba.kedditbysteps.commons.RedditNews
import com.droidcba.kedditbysteps.features.news.NewsManager
import com.droidcba.kedditbysteps.util.MockedCall
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.experimental.runBlocking
import org.jetbrains.spek.api.Spek
import java.util.*
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

/**
 * Unit Tests for NewsManager using Spek
 *
 * @author juancho.
 */
class NewsManagerSpekTest : Spek({

    given("a NewsManager") {
        var redditNews: RedditNews? = null
        var apiMock = mock<NewsAPI>()

        beforeEach {
            redditNews = null
            apiMock = mock<NewsAPI>()
        }

        on("service returns something") {
            beforeEach {
                // prepare
                val redditNewsResponse = RedditNewsResponse(RedditDataResponse(listOf(), null, null))
                val callMock = MockedCall<RedditNewsResponse>(redditNewsResponse)
                whenever(apiMock.getNews(any(), any())).thenReturn(callMock)

                // call
                val newsManager = NewsManager(apiMock)
                runBlocking {
                    redditNews = newsManager.getNews("")
                }
            }

            it("should receive something and no errors") {
                assertNotNull(redditNews)
            }
        }

        on("service returns just one news") {
            val newsData = RedditNewsDataResponse(
                    "author",
                    "title",
                    10,
                    Date().time,
                    "thumbnail",
                    "url"
            )
            beforeEach {
                // prepare
                val newsResponse = RedditChildrenResponse(newsData)
                val redditNewsResponse = RedditNewsResponse(RedditDataResponse(listOf(newsResponse), null, null))
                val callMock = MockedCall<RedditNewsResponse>(redditNewsResponse)
                whenever(apiMock.getNews(any(), any())).thenReturn(callMock)

                // call
                val newsManager = NewsManager(apiMock)
                runBlocking {
                    redditNews = newsManager.getNews("")
                }
            }

            it("should process only one news successfully") {
                assertNotNull(redditNews)
                assert(redditNews!!.news[0].author == newsData.author)
                assert(redditNews!!.news[0].title == newsData.title)
            }
        }

        on("service returns a 500 error") {
            var newsManager: NewsManager? = null

            beforeEach {
                // prepare
                val callMock = MockedCall<RedditNewsResponse>(exception = Throwable())
                whenever(apiMock.getNews(any(), any())).thenReturn(callMock)

                // call
                newsManager = NewsManager(apiMock)
            }

            it("should receive an exception") {
                assertFailsWith<Throwable> {
                    runBlocking {
                        redditNews = newsManager!!.getNews("")
                    }
                }
            }
        }
    }
})