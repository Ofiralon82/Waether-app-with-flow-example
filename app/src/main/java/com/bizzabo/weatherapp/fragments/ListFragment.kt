package com.bizzabo.weatherapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bizzabo.weatherapp.adapters.Degree
import com.bizzabo.weatherapp.adapters.WeatherListAdapter
import com.bizzabo.weatherapp.databinding.FragmentListBinding
import com.bizzabo.weatherapp.viewmodels.CitiesListViewModel
import com.bizzabo.weatherapp.viewmodels.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ListFragment : Fragment() {
    @Inject
    lateinit var modelFactory: ViewModelFactory
    private lateinit var viewModel: CitiesListViewModel
    private val citiesListAdapter = WeatherListAdapter(arrayListOf(), Degree.FAHRENHEIT)
    private lateinit var binding: FragmentListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        initViewModels()
        viewModel.getCitiesList(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
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
            adapter = citiesListAdapter
        }

        binding.refreshButton.setOnClickListener {
            binding.loadingView.visibility = View.VISIBLE
            binding.locationSearchView.setQuery("", false)
            binding.locationSearchView.clearFocus()
            viewModel.getCitiesList(true)
        }

        binding.fahrenheitButton.setOnClickListener {
            citiesListAdapter.convertDegree(Degree.FAHRENHEIT)
        }

        binding.celsiusButton.setOnClickListener {
            citiesListAdapter.convertDegree(Degree.CELSIUS)
        }

        binding.locationSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                citiesListAdapter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty()) {
                    citiesListAdapter.filter(newText)
                } else {
                    citiesListAdapter.filter("")
                }
                return true
            }
        })
    }

    private fun observeViewModel() {
        viewModel.weatherListLiveData.observe(viewLifecycleOwner, { list ->
            list?.let {
                binding.weatherRecyclerView.visibility = View.VISIBLE
                binding.listError.visibility = View.GONE
                binding.loadingView.visibility = View.GONE
                citiesListAdapter.updateWeatherList(list)
            }
        })

        viewModel.weatherListErrorLiveData.observe(viewLifecycleOwner, {
            binding.weatherRecyclerView.visibility = View.GONE
            binding.listError.visibility = View.VISIBLE
            binding.loadingView.visibility = View.GONE
        })
    }

    private fun initViewModels() {
        viewModel = ViewModelProvider(this, modelFactory).get(CitiesListViewModel::class.java)
    }
}