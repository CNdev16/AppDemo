package com.example.appdemo.di

import com.example.appdemo.ui.place.viewmodel.PlaceListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PlaceListViewModel(get()) }
}