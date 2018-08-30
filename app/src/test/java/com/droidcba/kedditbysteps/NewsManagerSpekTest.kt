package com.droidcba.kedditbysteps

import com.droidcba.kedditbysteps.api.*
import com.droidcba.kedditbysteps.commons.RedditNews
import com.droidcba.kedditbysteps.features.news.NewsManager
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.experimental.runBlocking
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.*
import kotlin.test.assertFailsWith

/**
 * Unit Tests for NewsManager using Spek
 *
 * @author juancho.
 * @update liemvo
 */

@RunWith(JUnitPlatform::class)
class NewsManagerSpekTest : Spek({

    given("a NewsManager") {
        var redditNews: RedditNews? = null
        var apiMock: NewsAPI

        beforeEachTest {
            redditNews = null
        }

        on("service returns something") {

            // prepare
            val redditNewsResponse = RedditNewsResponse(RedditDataResponse(listOf(), null, null))
            apiMock = mock {
                onBlocking { getNews(any(), any()) } doReturn redditNewsResponse
            }

            // call
            val newsManager = NewsManager(apiMock)
            runBlocking {
                redditNews = newsManager.getNews("")
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

            // prepare
            val newsResponse = RedditChildrenResponse(newsData)
            val redditNewsResponse = RedditNewsResponse(RedditDataResponse(listOf(newsResponse), null, null))
            apiMock = mock {
                onBlocking { getNews(any(), any()) } doReturn redditNewsResponse
            }
            // call
            val newsManager = NewsManager(apiMock)
            runBlocking {
                redditNews = newsManager.getNews("")
            }


            it("should process only one news successfully") {
                assertNotNull(redditNews)
                assert(redditNews!!.news[0].author == newsData.author)
                assert(redditNews!!.news[0].title == newsData.title)
            }
        }

        on("service returns a 500 error") {
            var newsManager: NewsManager? = null


            // prepare
            apiMock = mock {
                onBlocking { getNews(any(), any()) } doAnswer { throw Throwable() }
            }

            // call
            newsManager = NewsManager(apiMock)

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