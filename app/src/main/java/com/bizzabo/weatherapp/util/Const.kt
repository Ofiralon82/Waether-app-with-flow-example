package com.bizzabo.weatherapp.util

object AppConstants {
    const val APP_ID = "0eaed6b0919bf5286c47e4deb501f325"
    const val citiesIds = "2643743,293396,5128581,2800866,3128760,2988507,1850147,1816670,2147714,3432043,4164138,6173331,524901,1609350,993800,2464470,1701668"
    const val SHARED_PREFERENCES_KEY = "WEATHER_APP_SHARED_PREFERENCES_KEY"
    const val UPDATED_TIME_KEY = "UPDATED_TIME_KEY"
    const val TIME_TO_REFRESH = 1000 * 60
}

object DateConstants {
    const val DAY_SECONDS: Long = 24 * 60 * 60
    const val DATE_FORMAT_DAY = "EEE, dd/MM"
    const val SECOND_IN_MILLISECOND = 1000
    const val NUMBER_OF_DAYS_IN_WEEK = 5
}

object IconConstants {
    const val DEFAULT_WEATHER_ICON = "03d.png"
    const val WEATHER_ICON_URL = "https://openweathermap.org/img/wn/"
    const val ICON_SUFFIX = ".png"
}

object DegreeHelper {
    const val UNIT_FAHRENHEIT = "imperial"

    fun convertFahrenheitToCelsius(degree: Float): Float {
        return (degree - 32f) * (5f / 9f)
    }
}