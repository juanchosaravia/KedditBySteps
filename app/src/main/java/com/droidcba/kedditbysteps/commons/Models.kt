package com.droidcba.kedditbysteps.commons

import com.droidcba.kedditbysteps.commons.adapter.AdapterConstants
import com.droidcba.kedditbysteps.commons.adapter.ViewType

data class RedditNews(
        val after: String,
        val before: String,
        val news: List<RedditNewsItem>)

data class RedditNewsItem(
        val author: String,
        val title: String,
        val numComments: Int,
        val created: Long,
        val thumbnail: String,
        val url: String
) : ViewType {
    override fun getViewType() = AdapterConstants.NEWS
}