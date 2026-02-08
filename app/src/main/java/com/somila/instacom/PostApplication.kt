package com.somila.instacom

import android.app.Application
import com.somila.instacom.ui.theme.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PostApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PostApplication)
            modules(appModule)
        }
    }
}