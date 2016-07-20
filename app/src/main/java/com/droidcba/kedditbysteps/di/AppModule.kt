package com.droidcba.kedditbysteps.di

import android.content.Context
import com.droidcba.kedditbysteps.KedditApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *
 * @author juancho.
 */
@Module
class AppModule(val app: KedditApp) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return app;
    }

    @Provides
    @Singleton
    fun provideApplication(): KedditApp {
        return app;
    }
}