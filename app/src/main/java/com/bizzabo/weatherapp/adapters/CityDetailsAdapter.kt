package com.bizzabo.weatherapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bizzabo.weatherapp.R
import com.bizzabo.weatherapp.databinding.ItemForecastBinding
import com.bizzabo.weatherapp.model.CityForecast
import com.bizzabo.weatherapp.util.DegreeHelper
import com.bizzabo.weatherapp.util.IconConstants
import com.bumptech.glide.Glide

class CityDetailsAdapter(
    private val cityForecastData: ArrayList<CityForecast>,
    var degree: Degree
) :
    RecyclerView.Adapter<CityDetailsAdapter.ItemForecastViewHolder>() {

    fun updateCityForecast(newCityForecast: List<CityForecast>) {
        cityForecastData.clear()
        cityForecastData.addAll(newCityForecast)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemForecastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemForecastViewHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.item_forecast,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = cityForecastData.size

    override fun onBindViewHolder(holder: ItemForecastViewHolder, position: Int) {
        holder.view.date.text = cityForecastData[position].day

        holder.view.humidity.text = cityForecastData[position].humidity.toString()

        showTemperature(position, holder)

        val icon = cityForecastData[position].descriptionIcon

        Glide.with(holder.view.imageView)
            .load("${IconConstants.WEATHER_ICON_URL}$icon${IconConstants.ICON_SUFFIX}")
            .into(holder.view.imageView)
    }

    //todo: in the real world, I would have prepare the celsius values within the viewmodel
    private fun showTemperature(
        position: Int,
        holder: ItemForecastViewHolder
    ) {
        val feelsLike = cityForecastData[position].feelsLike
        holder.view.feelsLikeText.text =
            holder.view.feelsLikeText.context.getString(
                if (degree == Degree.FAHRENHEIT) R.string.city_details_feels_like_fahrenheit else R.string.city_details_feels_like_celsius,
                if (degree == Degree.FAHRENHEIT) feelsLike else DegreeHelper.convertFahrenheitToCelsius(
                    feelsLike
                )
            )
    }

    class ItemForecastViewHolder(var view: ItemForecastBinding) : RecyclerView.ViewHolder(view.root)
}