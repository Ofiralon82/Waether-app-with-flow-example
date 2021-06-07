//package com.bizzabo.weatherapp.usecases
//
//import com.bizzabo.weatherapp.model.WeatherListResponse
//import com.bizzabo.weatherapp.retrofit.api.WeatherListApi
//import com.bizzabo.weatherapp.util.AppConstants
//import com.bizzabo.weatherapp.util.DegreeHelper
//import io.reactivex.rxjava3.core.Single
//import javax.inject.Inject
//
//class CityDetailsUseCase @Inject constructor(private val weatherListApi: WeatherListApi) {
//    fun execute(cityName: String): Single<WeatherListResponse> {
//        return weatherListApi.getCityForecastData(
//            cityName,
//            AppConstants.APP_ID,
//            DegreeHelper.UNIT_FAHRENHEIT
//        )
//    }
//}