package com.somila.instacom.ui.theme.landingscreen.di

import com.somila.instacom.ui.theme.landingscreen.LandingScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val landingModule = module {
    viewModel { LandingScreenViewModel(get()) }
}