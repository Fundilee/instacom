package com.somila.landing.di

import com.somila.landing.PostLandingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val postLandingModule = module {
    viewModel { PostLandingViewModel(get()) }
}