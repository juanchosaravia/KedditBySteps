package com.droidcba.kedditbysteps.di.news

import com.droidcba.kedditbysteps.di.AppModule
import com.droidcba.kedditbysteps.di.NetworkModule
import com.droidcba.kedditbysteps.features.news.NewsFragment
import dagger.Component
import javax.inject.Singleton

/**
 * News Component
 *
 * @author juancho.
 */
@Singleton
@Component(modules = [AppModule::class, NewsModule::class, NetworkModule::class])
interface NewsComponent {

    fun inject(newsFragment: NewsFragment)

}