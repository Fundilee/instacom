package com.somila.details.di

import com.somila.details.PostDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val postDetailsModule = module {
    viewModel { PostDetailsViewModel(get(), get()) }
}