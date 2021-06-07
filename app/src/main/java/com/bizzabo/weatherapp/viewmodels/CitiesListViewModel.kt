package com.bizzabo.weatherapp.viewmodels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bizzabo.weatherapp.R
import com.bizzabo.weatherapp.model.WeatherListResponse
import com.bizzabo.weatherapp.model.WeatherResponse
import com.bizzabo.weatherapp.room.CitiesWeatherDatabase
import com.bizzabo.weatherapp.usecases.CitiesListUseCase
import com.bizzabo.weatherapp.util.AppConstants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class CitiesListViewModel @Inject constructor(
    application: Application,
    private val citiesListUseCase: CitiesListUseCase,
) :
    AndroidViewModel(application) {
    val sharedPreferences: SharedPreferences =
        getApplication<Application>().getSharedPreferences(
            AppConstants.SHARED_PREFERENCES_KEY,
            Context.MODE_PRIVATE
        )

    private val weatherListMutableLiveData =
        MutableLiveData<List<WeatherResponse>>()//todo: in the real world, should have map to ui model (like I did with CityDetailsMapper)
    val weatherListLiveData: LiveData<List<WeatherResponse>>
        get() = weatherListMutableLiveData

    private val weatherListErrorMutableLiveData = MutableLiveData<Boolean>()
    val weatherListErrorLiveData: LiveData<Boolean>
        get() = weatherListErrorMutableLiveData

    private val disposable = CompositeDisposable()

    fun getCitiesList(hardRefresh: Boolean) {
        val updatedTime = sharedPreferences.getLong(AppConstants.UPDATED_TIME_KEY, 0)
        if (updatedTime != 0L && System.currentTimeMillis() - updatedTime < AppConstants.TIME_TO_REFRESH && !hardRefresh) {
            fetchFromDB()
        } else {
            fetchFromApi()
        }
    }

    private fun fetchFromDB() {
        GlobalScope.launch {
            weatherListMutableLiveData.postValue(
                CitiesWeatherDatabase(getApplication()).CitiesListDao().getAllCities()
            )
        }
        Toast.makeText(
            getApplication(),
            getApplication<Application>().getString(R.string.cities_retrieved_from_database),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun fetchFromApi() {
        disposable.add(
            citiesListUseCase.execute()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherListResponse>() {
                    override fun onSuccess(weatherResponse: WeatherListResponse) {
                        weatherListMutableLiveData.value = weatherResponse.citiesResponse
                        Toast.makeText(
                            getApplication(),
                            getApplication<Application>().getString(R.string.cities_retrieved_from_api),
                            Toast.LENGTH_SHORT
                        ).show()
                        GlobalScope.launch {
                            val dao = CitiesWeatherDatabase(getApplication()).CitiesListDao()
                            dao.deleteAllCities()
                            dao.insertAll(*weatherResponse.citiesResponse.toTypedArray())
                            sharedPreferences.edit()
                                .putLong(AppConstants.UPDATED_TIME_KEY, System.currentTimeMillis())
                                .apply()
                        }
                    }

                    override fun onError(e: Throwable) {
                        val updatedTime =
                            sharedPreferences.getLong(AppConstants.UPDATED_TIME_KEY, 0)
                        if (updatedTime > 0) {
                            fetchFromDB()
                        } else {
                            weatherListErrorMutableLiveData.value = true
                        }
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}