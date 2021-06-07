package com.bizzabo.weatherapp.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bizzabo.weatherapp.R
import com.bizzabo.weatherapp.databinding.ItemWeatherBinding
import com.bizzabo.weatherapp.fragments.ListFragmentDirections
import com.bizzabo.weatherapp.model.WeatherResponse
import com.bizzabo.weatherapp.util.DegreeHelper
import com.bizzabo.weatherapp.util.IconConstants
import com.bumptech.glide.Glide
import java.util.*
import kotlin.collections.ArrayList

class WeatherListAdapter(
    private val weatherlist: ArrayList<WeatherResponse>,
    private var degree: Degree
) :
    RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder>() {

    var originalWeatherlist: ArrayList<WeatherResponse> = ArrayList()

    fun updateWeatherList(newWeatherlist: List<WeatherResponse>) {
        weatherlist.clear()
        weatherlist.addAll(newWeatherlist)
        originalWeatherlist.clear()
        originalWeatherlist.addAll(newWeatherlist)
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        weatherlist.clear()
        if (query.isEmpty()) {
            weatherlist.addAll(originalWeatherlist)
            notifyDataSetChanged()
            return
        }

        for (weather in originalWeatherlist) {
            val cityName = weather.name?.toLowerCase(Locale.ROOT)
            cityName?.let {
                if (it.startsWith(query)) {
                    weatherlist.add(weather)
                }
            }
        }
        notifyDataSetChanged()
    }

    fun convertDegree(degree: Degree) {
        this.degree = degree
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return WeatherViewHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.item_weather,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = weatherlist.size

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.view.name.text = weatherlist[position].name

        holder.view.description.text =
            if (weatherlist[position].weather.size > 0) weatherlist[position].weather[0].description else ""

        showTemperature(position, holder)

        val icon =
            if (weatherlist[position].weather.size > 0) weatherlist[position].weather[0].icon else IconConstants.DEFAULT_WEATHER_ICON

        Glide.with(holder.view.imageView)
            .load("${IconConstants.WEATHER_ICON_URL}$icon${IconConstants.ICON_SUFFIX}")
            .into(holder.view.imageView)

        holder.view.mapButton.setOnClickListener {
            showGoogleMaps(position, holder)
        }

        holder.view.itemContainer.setOnClickListener { view ->
            weatherlist[position].name?.let {
                Navigation.findNavController(view)
                    .navigate(
                        ListFragmentDirections.actionListFragmentToDetailsFragment(
                            it,
                            degree
                        )
                    )
            }
        }
    }

    //todo: in the real world, I would have prepare the celsius values within the viewmodel
    private fun showTemperature(position: Int, holder: WeatherViewHolder) {
        val min = weatherlist[position].main?.tempMin
        val max = weatherlist[position].main?.tempMax
        if (min != null && max != null) {
            holder.view.temperature.text =
                holder.view.temperature.context.getString(
                    if (degree == Degree.FAHRENHEIT) R.string.city_temperatures_fahrenheit else R.string.city_temperatures_celsius,
                    if (degree == Degree.FAHRENHEIT) min else DegreeHelper.convertFahrenheitToCelsius(
                        min
                    ),
                    if (degree == Degree.FAHRENHEIT) max else DegreeHelper.convertFahrenheitToCelsius(
                        max
                    )
                )
        } else {
            holder.view.temperature.text = ""
        }
    }

    private fun showGoogleMaps(position: Int, holder: WeatherViewHolder) {
        weatherlist[position].coord?.let { it ->
            val gmmIntentUri = Uri.parse("geo:${it.lat},${it.lon}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            holder.view.mapButton.context?.packageManager?.let { packageManager ->
                mapIntent.resolveActivity(packageManager)?.let {
                    holder.view.mapButton.context.startActivity(mapIntent)
                }
            }
        }
    }

    class WeatherViewHolder(var view: ItemWeatherBinding) : RecyclerView.ViewHolder(view.root)
}

enum class Degree {
    CELSIUS, FAHRENHEIT
}