package com.example.appdemo

import android.app.Application
import com.example.appdemo.di.appModule
import com.example.appdemo.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AppDemo: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppDemo)
            androidLogger(Level.DEBUG)
            modules(listOf(appModule, viewModelModule))
        }
    }
}