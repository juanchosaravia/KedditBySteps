package com.droidcba.kedditbysteps.di.news

import com.droidcba.kedditbysteps.api.NewsAPI
import com.droidcba.kedditbysteps.api.NewsRestAPI
import com.droidcba.kedditbysteps.api.RedditApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class NewsModule {

    @Provides
    @Singleton
    fun provideNewsAPI(redditApi: RedditApi): NewsAPI = NewsRestAPI(redditApi)

    @Provides
    @Singleton
    fun provideRedditApi(retrofit: Retrofit): RedditApi = retrofit.create(RedditApi::class.java)

}
