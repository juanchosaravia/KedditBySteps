package com.droidcba.kedditbysteps

import android.app.Application
import com.droidcba.kedditbysteps.di.AppModule
import com.droidcba.kedditbysteps.di.news.DaggerNewsComponent
import com.droidcba.kedditbysteps.di.news.NewsComponent

/**
 *
 * @author juancho.
 */
class KedditApp : Application() {

    companion object {
        lateinit var newsComponent: NewsComponent
    }

    override fun onCreate() {
        super.onCreate()
        newsComponent = DaggerNewsComponent.builder()
                .appModule(AppModule(this))
                //.newsModule(NewsModule()) Module with empty constructor is implicitly created by dagger.
                .build()
    }
}