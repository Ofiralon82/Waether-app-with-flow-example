package com.bizzabo.weatherapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bizzabo.weatherapp.model.WeatherResponse

@Dao
interface CitiesListDao {
    @Insert
    suspend fun insertAll(vararg cities: WeatherResponse): List<Long>

    @Query("SELECT * FROM weatherResponse")
    suspend fun getAllCities(): List<WeatherResponse>

    @Query("DELETE FROM weatherResponse")
    suspend fun deleteAllCities()
}