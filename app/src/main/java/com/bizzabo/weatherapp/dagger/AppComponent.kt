package com.bizzabo.weatherapp.dagger

import android.app.Application
import android.content.Context
import com.bizzabo.weatherapp.application.App
import com.bizzabo.weatherapp.dagger.modules.ActivityBindingModule
import com.bizzabo.weatherapp.dagger.modules.CitiesModule
import com.bizzabo.weatherapp.dagger.modules.FragmentBindingModule
import com.bizzabo.weatherapp.dagger.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ActivityBindingModule::class,
        FragmentBindingModule::class,
        AndroidInjectionModule::class,
        CitiesModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }
}
