package com.droidcba.kedditbysteps.di

import android.app.Application
import android.content.Context
import com.droidcba.kedditbysteps.app.KedditApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: KedditApp) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideApplication() : Application = app
}
