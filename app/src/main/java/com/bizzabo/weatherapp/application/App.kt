package com.bizzabo.weatherapp.application

import com.bizzabo.weatherapp.dagger.AppComponent
import com.bizzabo.weatherapp.dagger.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {

    private val mInjector: AppComponent = DaggerAppComponent.builder()
        .application(this)
        .context(this)
        .build()

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return mInjector
    }
}