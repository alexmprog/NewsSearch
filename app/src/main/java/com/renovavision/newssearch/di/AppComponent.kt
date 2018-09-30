package com.renovavision.newssearch.di

import android.app.Application
import com.renovavision.newssearch.App
import com.renovavision.newssearch.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(AndroidSupportInjectionModule::class),
    (ActivityBuildersModule::class), (ViewModelsModule::class), (AppModule::class),
    (DataModule::class), (ApiModule::class)])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}