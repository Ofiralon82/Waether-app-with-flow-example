package com.bizzabo.weatherapp.retrofit.api

import com.bizzabo.weatherapp.model.WeatherListResponse
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherListApi {
    @GET("group?")
    fun getCurrentWeatherData(
        @Query("id") lat: String,
        @Query("appid") appId: String,
        @Query("units") unit: String
    ): Single<WeatherListResponse>

    @GET("forecast?")
    fun getCityForecastData(
        @Query("q") cityName: String,
        @Query("appid") appId: String,
        @Query("units") unit: String
    ): Single<WeatherListResponse>

    @GET("forecast?")
    suspend fun getCityForecastData2(
        @Query("q") cityName: String,
        @Query("appid") appId: String,
        @Query("units") unit: String
    ): WeatherListResponse

    @GET("forecast22?")
    suspend fun getCityForecastData2Error(
        @Query("q") cityName: String,
        @Query("appid") appId: String,
        @Query("units") unit: String
    ): WeatherListResponse
}