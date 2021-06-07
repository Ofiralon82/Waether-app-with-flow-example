//package com.bizzabo.weatherapp.mapper
//
//import com.bizzabo.weatherapp.model.CityDetailsUiModel
//import com.bizzabo.weatherapp.model.CityForecast
//import com.bizzabo.weatherapp.model.WeatherResponse
//import com.bizzabo.weatherapp.util.DateConstants
//import java.text.SimpleDateFormat
//import java.util.*
//import javax.inject.Inject
//
//interface CityDetailsMapper {
//    fun map(name: String, data: List<WeatherResponse>): CityDetailsUiModel
//}
//
//class CityDetailsMapperImpl @Inject constructor() : CityDetailsMapper {
//    override fun map(name: String, data: List<WeatherResponse>): CityDetailsUiModel {
//        return CityDetailsUiModel(
//            cityName = name,
//            description = data.getOrNull(0)?.weather?.getOrNull(0)?.description ?: "",
//            descriptionIcon = data.getOrNull(0)?.weather?.getOrNull(0)?.icon ?: "",
//            min = data.getOrNull(0)?.main?.tempMin ?: 0.0f,
//            max = data.getOrNull(0)?.main?.tempMax ?: 0.0f,
//            cityForecast = getWeekForecast(data.getOrNull(0)?.dt, data)
//        )
//    }
//
//    private fun getWeekForecast(dt: Long?, data: List<WeatherResponse>): List<CityForecast> {
//        if (dt == null) {
//            return listOf()
//        }
//        val weekForecast = mutableListOf<CityForecast>()
//        for (time in dt..dt + (DateConstants.DAY_SECONDS * DateConstants.NUMBER_OF_DAYS_IN_WEEK) step DateConstants.DAY_SECONDS) {
//            val currWeather = data.filter { it.dt == time }
//            if (currWeather.isNotEmpty()) {
//                weekForecast.add(
//                    CityForecast(
//                        SimpleDateFormat(DateConstants.DATE_FORMAT_DAY, Locale.ROOT).format(Date(time*DateConstants.SECOND_IN_MILLISECOND)),
//                        currWeather[0].weather.getOrNull(0)?.icon ?: "",
//                        currWeather[0].main?.humidity?.toInt() ?: 0,
//                        currWeather[0].main?.feelsLike ?: 0.0f
//                    )
//                )
//            }
//        }
//
//        return weekForecast
//    }
//}