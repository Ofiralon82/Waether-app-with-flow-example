//package com.bizzabo.weatherapp.viewmodels
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.bizzabo.weatherapp.mapper.CityDetailsMapper
//import com.bizzabo.weatherapp.model.CityDetailsUiModel
//import com.bizzabo.weatherapp.model.WeatherListResponse
//import com.bizzabo.weatherapp.usecases.CityDetailsUseCase
//import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
//import io.reactivex.rxjava3.disposables.CompositeDisposable
//import io.reactivex.rxjava3.observers.DisposableSingleObserver
//import io.reactivex.rxjava3.schedulers.Schedulers
//import javax.inject.Inject
//
//class CityDetailsViewModel @Inject constructor(
//    private val cityDetailsUseCase: CityDetailsUseCase,
//    private val cityDetailsMapper: CityDetailsMapper
//) :
//    ViewModel() {
//
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
//}