package com.bizzabo.weatherapp.dagger.modules

import com.bizzabo.weatherapp.mapper.CityDetailsMapper
import com.bizzabo.weatherapp.mapper.CityDetailsMapperImpl
import com.bizzabo.weatherapp.retrofit.RetrofitClients
import com.bizzabo.weatherapp.retrofit.api.WeatherListApi
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
object CitiesModule {
    @Provides
    fun citiesApi(): WeatherListApi {
        return RetrofitClients.api
            .create(WeatherListApi::class.java)
    }

    @Provides
    fun cityDetailsMapper(): CityDetailsMapper {
        return CityDetailsMapperImpl()
    }

    @Provides
    fun ioDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}