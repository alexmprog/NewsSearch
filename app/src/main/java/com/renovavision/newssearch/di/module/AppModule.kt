package com.renovavision.newssearch.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.renovavision.newssearch.utils.Utils
import com.renovavision.newssearch.utils.prefs.Prefs
import com.renovavision.newssearch.utils.scheduler.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return SchedulerProvider(Schedulers.io(), AndroidSchedulers.mainThread())
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return Prefs.defaultPrefs(context)
    }

    @Provides
    @Singleton
    fun provideUtils(application: Application): Utils = Utils(application)

}