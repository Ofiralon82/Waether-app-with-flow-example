package com.bizzabo.weatherapp.model

data class CityDetailsUiModel(
    val cityName: String,
    var description: String,
    val descriptionIcon: String,
    val min: Float,
    val max: Float,
    val cityForecast: List<CityForecast>
)

data class CityForecast(
    val day: String,
    val descriptionIcon: String,
    val humidity: Int,
    val feelsLike: Float
)