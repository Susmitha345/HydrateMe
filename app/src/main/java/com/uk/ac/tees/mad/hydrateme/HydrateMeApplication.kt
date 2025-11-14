package com.uk.ac.tees.mad.hydrateme

import android.app.Application
import com.uk.ac.tees.mad.hydrateme.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HydrateMeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@HydrateMeApplication)
            modules(appModule)
        }
    }
}
