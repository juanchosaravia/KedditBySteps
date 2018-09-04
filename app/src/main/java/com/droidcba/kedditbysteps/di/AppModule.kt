package com.droidcba.kedditbysteps.di

import android.app.Application
import android.content.Context
import com.droidcba.kedditbysteps.KedditApp
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.experimental.CommonPool
import javax.inject.Singleton
import kotlin.coroutines.experimental.CoroutineContext

/**
 *
 * @author juancho.
 */
@Module
class AppModule(val app: KedditApp) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideApplication(): Application = app

    @Provides
    fun provideCoroutineContext(): CoroutineContext = CommonPool
}
