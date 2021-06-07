package com.bizzabo.weatherapp.dagger.modules

import androidx.lifecycle.ViewModel
import com.bizzabo.weatherapp.viewmodels.CitiesListViewModel
import com.bizzabo.weatherapp.viewmodels.CityDetailsViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CitiesListViewModel::class)
    abstract fun citiesListViewModel(citiesListViewModel: CitiesListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CityDetailsViewModel::class)
    abstract fun cityDetailsViewModel(cityDetailsViewModel: CityDetailsViewModel): ViewModel

}

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
