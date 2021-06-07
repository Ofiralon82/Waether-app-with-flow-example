package com.bizzabo.weatherapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bizzabo.weatherapp.R
import com.bizzabo.weatherapp.adapters.CityDetailsAdapter
import com.bizzabo.weatherapp.adapters.Degree
import com.bizzabo.weatherapp.databinding.FragmentDetailsBinding
import com.bizzabo.weatherapp.util.DegreeHelper
import com.bizzabo.weatherapp.util.IconConstants
import com.bizzabo.weatherapp.viewmodels.CityDetailsViewModel
import com.bizzabo.weatherapp.viewmodels.ViewModelFactory
import com.bumptech.glide.Glide
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsFragment : Fragment() {
    @Inject
    lateinit var modelFactory: ViewModelFactory
    private lateinit var viewModel: CityDetailsViewModel
    private lateinit var cityDetailsAdapter: CityDetailsAdapter
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        initViewModels()
        viewModel.getCityDetails(arguments?.getString(CITY_NAME) ?: "")
        cityDetailsAdapter = CityDetailsAdapter(arrayListOf(), arguments?.get(DEGREE) as Degree)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        initViews()
    }

    private fun initViews() {
        binding.listError.visibility = View.GONE

        binding.weatherRecyclerView.apply {
            adapter = cityDetailsAdapter
        }

        val cityName = arguments?.getString(CITY_NAME) ?: ""
        binding.detailsToolbar.title = cityName
        binding.detailsToolbar.setNavigationOnClickListener { activity?.onBackPressed() }
        binding.refreshButton.setOnClickListener {
            binding.loadingView.visibility = View.VISIBLE
            viewModel.getCityDetails(cityName)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.myUiState.collect { cityDetailsUiModel ->
                cityDetailsUiModel?.let {
                    binding.weatherRecyclerView.visibility = View.VISIBLE
                    binding.listError.visibility = View.GONE
                    binding.loadingView.visibility = View.GONE
                    cityDetailsAdapter.updateCityForecast(it.cityForecast)

                    binding.name.text = it.cityName
                    binding.description.text = it.description
                    val currDegree = arguments?.get(DEGREE)
                    binding.temperatures.text = context?.getString(
                        if (currDegree == Degree.FAHRENHEIT) R.string.city_temperatures_fahrenheit else R.string.city_temperatures_celsius,
                        if (currDegree == Degree.FAHRENHEIT) it.min else DegreeHelper.convertFahrenheitToCelsius(
                            it.min
                        ),
                        if (currDegree == Degree.FAHRENHEIT) it.max else DegreeHelper.convertFahrenheitToCelsius(
                            it.max
                        )
                    )
                    Glide.with(binding.image)
                        .load("${IconConstants.WEATHER_ICON_URL}${it.descriptionIcon}${IconConstants.ICON_SUFFIX}")
                        .into(binding.image)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.myErrorUiState.collect { koko ->
                if (koko) {
                    Log.e("fds", "fds")
                }
                binding.weatherRecyclerView.visibility = View.GONE
                binding.listError.visibility = View.VISIBLE
                binding.loadingView.visibility = View.GONE
            }
        }


//        viewModel.cityDetailsLiveData.observe(viewLifecycleOwner, { cityDetailsUiModel ->
//            cityDetailsUiModel?.let {
//                binding.weatherRecyclerView.visibility = View.VISIBLE
//                binding.listError.visibility = View.GONE
//                binding.loadingView.visibility = View.GONE
//                cityDetailsAdapter.updateCityForecast(it.cityForecast)
//
//                binding.name.text = it.cityName
//                binding.description.text = it.description
//                val currDegree = arguments?.get(DEGREE)
//                binding.temperatures.text = context?.getString(
//                    if (currDegree == Degree.FAHRENHEIT) R.string.city_temperatures_fahrenheit else R.string.city_temperatures_celsius,
//                    if (currDegree == Degree.FAHRENHEIT) it.min else DegreeHelper.convertFahrenheitToCelsius(
//                        it.min
//                    ),
//                    if (currDegree == Degree.FAHRENHEIT) it.max else DegreeHelper.convertFahrenheitToCelsius(
//                        it.max
//                    )
//                )
//                Glide.with(binding.image)
//                    .load("${IconConstants.WEATHER_ICON_URL}${it.descriptionIcon}${IconConstants.ICON_SUFFIX}")
//                    .into(binding.image)
//            }
//        })
//
//        viewModel.cityDetailsErrorLiveData.observe(viewLifecycleOwner, {
//            binding.weatherRecyclerView.visibility = View.GONE
//            binding.listError.visibility = View.VISIBLE
//            binding.loadingView.visibility = View.GONE
//        })
    }

    private fun initViewModels() {
        viewModel = ViewModelProvider(this, modelFactory).get(CityDetailsViewModel::class.java)
    }

    companion object {
        private const val CITY_NAME = "name"
        private const val DEGREE = "degree"
    }
}