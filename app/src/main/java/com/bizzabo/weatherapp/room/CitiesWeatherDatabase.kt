package com.bizzabo.weatherapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bizzabo.weatherapp.model.WeatherResponse

@Database(entities = [WeatherResponse::class], version = 1)
abstract class CitiesWeatherDatabase : RoomDatabase() {
    abstract fun CitiesListDao(): CitiesListDao

    companion object {
        @Volatile
        private var instance: CitiesWeatherDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CitiesWeatherDatabase::class.java,
            "weatherdatabase"
        ).build()
    }
}