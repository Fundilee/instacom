package com.somila.instacom

import android.app.Application
import com.somila.data.di.dataModule
import com.somila.details.di.postDetailsModule
import com.somila.instacom.di.appModule
import com.somila.landing.di.postLandingModule
import com.somila.network.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PostApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PostApplication)
            modules(appModule, networkModule, postLandingModule, postDetailsModule, dataModule)
        }
    }
}