package com.somila.instacom.ui.theme.di

import com.somila.instacom.domain.model.network.networkModule
import com.somila.instacom.ui.theme.landingscreen.di.landingModule
import org.koin.dsl.module

val appModule = module {
    includes(
        networkModule,
        landingModule
    )
}