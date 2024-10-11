package com.example1.newsapplication

import android.app.Application
import com.example1.newsapplication.di.appModule
import com.example1.newsapplication.di.repositoryModule
import com.example1.newsapplication.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApp)
            modules(appModule, viewModelModule, repositoryModule)
        }
    }
}