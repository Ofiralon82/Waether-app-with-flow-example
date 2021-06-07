package com.bizzabo.weatherapp.dagger.modules

import com.bizzabo.weatherapp.fragments.DetailsFragment
import com.bizzabo.weatherapp.fragments.ListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {
    @ContributesAndroidInjector
    abstract fun listFragment(): ListFragment

    @ContributesAndroidInjector
    abstract fun detailsFragment(): DetailsFragment
}