package com.bizzabo.weatherapp.model

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

data class WeatherListResponse(
    @SerializedName("list")
    var citiesResponse: List<WeatherResponse> = ArrayList()
)

@Entity
@TypeConverters(DataConverter::class)
data class WeatherResponse(
    @ColumnInfo(name = "dt")
    @SerializedName("dt")
    var dt: Long = 0,

    @Embedded
    @SerializedName("coord")
    var coord: Coord? = null,

    @SerializedName("weather")
    var weather: ArrayList<Weather> = ArrayList(),

    @Embedded
    @SerializedName("main")
    var main: Main? = null,

    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}

data class Weather(
    @ColumnInfo(name = "main_second")
    @SerializedName("main")
    var main: String? = null,

    @ColumnInfo(name = "description")
    @SerializedName("description")
    var description: String? = null,

    @ColumnInfo(name = "icon")
    @SerializedName("icon")
    var icon: String? = null
)

data class Main(
    @ColumnInfo(name = "temp")
    @SerializedName("temp")
    var temp: Float = 0.toFloat(),

    @ColumnInfo(name = "humidity")
    @SerializedName("humidity")
    var humidity: Float = 0.toFloat(),

    @ColumnInfo(name = "pressure")
    @SerializedName("pressure")
    var pressure: Float = 0.toFloat(),

    @ColumnInfo(name = "temp_min")
    @SerializedName("temp_min")
    var tempMin: Float = 0.toFloat(),

    @ColumnInfo(name = "temp_max")
    @SerializedName("temp_max")
    var tempMax: Float = 0.toFloat(),

    @ColumnInfo(name = "feels_like")
    @SerializedName("feels_like")
    var feelsLike: Float = 0.toFloat()
)

data class Coord(
    @ColumnInfo(name = "lon")
    @SerializedName("lon")
    var lon: Float = 0.toFloat(),

    @ColumnInfo(name = "lat")
    @SerializedName("lat")
    var lat: Float = 0.toFloat()
)

class DataConverter {
    @TypeConverter
    fun fromCountryLangList(value: ArrayList<Weather>): String {
        val type = object : TypeToken<ArrayList<Weather>>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toCountryLangList(value: String): ArrayList<Weather> {
        val type = object : TypeToken<ArrayList<Weather>>() {}.type
        return Gson().fromJson(value, type)
    }
}