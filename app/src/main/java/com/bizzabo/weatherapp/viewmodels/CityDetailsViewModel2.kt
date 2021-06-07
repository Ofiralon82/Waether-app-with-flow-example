package com.bizzabo.weatherapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bizzabo.weatherapp.mapper.CityDetailsMapper
import com.bizzabo.weatherapp.model.CityDetailsUiModel
import com.bizzabo.weatherapp.model.WeatherListResponse
import com.bizzabo.weatherapp.usecases.CityDetailsUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Exception
import javax.inject.Inject

class CityDetailsViewModel @Inject constructor(
    private val cityDetailsUseCase: CityDetailsUseCase
) :
    ViewModel() {

//    private val cityDetailsMutableLiveData = MutableLiveData<CityDetailsUiModel>()
//    val cityDetailsLiveData: LiveData<CityDetailsUiModel>
//        get() = cityDetailsMutableLiveData
//
//    private val cityDetailsErrorMutableLiveData = MutableLiveData<Boolean>()
//    val cityDetailsErrorLiveData: LiveData<Boolean>
//        get() = cityDetailsErrorMutableLiveData
//
//    private val disposable = CompositeDisposable()
//
//    fun getCityDetails(cityName: String) {
//        disposable.add(
//            cityDetailsUseCase.execute(cityName)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(object : DisposableSingleObserver<WeatherListResponse>() {
//                    override fun onSuccess(weatherResponse: WeatherListResponse) {
//                        cityDetailsMutableLiveData.value =
//                            cityDetailsMapper.map(cityName, weatherResponse.citiesResponse)
//                    }
//
//                    override fun onError(e: Throwable) {
//                        cityDetailsErrorMutableLiveData.value = true
//                    }
//                })
//        )
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        disposable.clear()
//    }

    var ttt = false
    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->

            if (ttt) {
                ttt = !ttt
                _myErrorUiState.value = true
//                _myUiState.value = CityDetailsUiModel("aytry", "b", "c", 1f, 2f, listOf())
            } else {
                ttt = !ttt
                _myErrorUiState.value = false
//                _myUiState.value = CityDetailsUiModel("agg666", "b", "c", 1f, 2f, listOf())
            }


    }


    private val _myUiState = MutableStateFlow(
        CityDetailsUiModel("a", "b", "c", 1f, 2f, listOf())
    )
    val myUiState: StateFlow<CityDetailsUiModel?> = _myUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CityDetailsUiModel("a", "b", "c", 1f, 2f, listOf())
    )

    private val _myErrorUiState = MutableStateFlow(true)
    val myErrorUiState: StateFlow<Boolean> = _myErrorUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )

    fun getCityDetails(cityName: String) {
        viewModelScope.launch(/*exceptionHandler*/) {
            withContext(Dispatchers.IO) {
//                try {
                    val kkk = cityDetailsUseCase.execute(cityName)
//                Log.e("titi", kkk.description)
                    kkk?.run { _myUiState.value = this }
//                } catch (e: Exception) {
//                    if (ttt) {
//                        ttt = !ttt
//                        _myErrorUiState.value = true
////                _myUiState.value = CityDetailsUiModel("aytry", "b", "c", 1f, 2f, listOf())
//                    } else {
//                        ttt = !ttt
//                        _myErrorUiState.value = false
////                _myUiState.value = CityDetailsUiModel("agg666", "b", "c", 1f, 2f, listOf())
//                    }
//                }
            }
        }
    }
}