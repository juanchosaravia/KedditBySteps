package com.droidcba.kedditbysteps.features.news

import com.droidcba.kedditbysteps.commons.RedditNews

/**
 * @author juan.saravia
 */
sealed class NewsState {
    class Success(val redditNews: RedditNews) : NewsState()
    class Error(val message: String?) : NewsState()
}