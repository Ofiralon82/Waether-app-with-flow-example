package com.bizzabo.weatherapp.usecases

import android.util.Log
import com.bizzabo.weatherapp.mapper.CityDetailsMapper
import com.bizzabo.weatherapp.model.CityDetailsUiModel
import com.bizzabo.weatherapp.model.WeatherListResponse
import com.bizzabo.weatherapp.retrofit.api.WeatherListApi
import com.bizzabo.weatherapp.util.AppConstants
import com.bizzabo.weatherapp.util.DegreeHelper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class CityDetailsUseCase @Inject constructor(
    private val weatherListApi: WeatherListApi,
    private val cityDetailsMapper: CityDetailsMapper,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
//    fun execute(cityName: String): Single<WeatherListResponse> {
//        return weatherListApi.getCityForecastData(
//            cityName,~
//            AppConstants.APP_ID,
//            DegreeHelper.UNIT_FAHRENHEIT
//        )
//    }

    var bb2 = false

    var forS = true
    suspend fun execute(cityName: String): CityDetailsUiModel? {
//        try {
        if (bb2) {
            bb2 = !bb2
            return flowOf(
                weatherListApi.getCityForecastData2Error(
                    cityName,
                    AppConstants.APP_ID,
                    DegreeHelper.UNIT_FAHRENHEIT
                )
            ).map {
                cityDetailsMapper.map(cityName, it.citiesResponse)
            }.single()
        } else {
            bb2 = !bb2
            if (forS) {
                forS = !forS
                return flowOf(
                    weatherListApi.getCityForecastData2(
                        cityName,
                        AppConstants.APP_ID,
                        DegreeHelper.UNIT_FAHRENHEIT
                    )
                ).map {
                    cityDetailsMapper.map(cityName, it.citiesResponse)
                }.single()
            } else {
                forS = !forS
                return flowOf(
                    weatherListApi.getCityForecastData2(
                        cityName,
                        AppConstants.APP_ID,
                        DegreeHelper.UNIT_FAHRENHEIT
                    )
                ).map {
                    var tt = cityDetailsMapper.map(cityName, it.citiesResponse)
                    tt.description = "gfsdfffff"
                    tt
                }.single()
            }
        }
    }
}