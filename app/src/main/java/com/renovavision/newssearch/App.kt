package com.renovavision.newssearch

import com.renovavision.newssearch.di.AppComponent
import com.renovavision.newssearch.di.DaggerAppComponent
import dagger.android.DaggerApplication

class App : DaggerApplication() {

    lateinit var appComponent: AppComponent

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
    }

    public override fun applicationInjector(): AppComponent {
        if (!::appComponent.isInitialized) {
            appComponent = DaggerAppComponent.builder()
                    .application(this)
                    .build()
        }
        return appComponent
    }

}