package com.cattishapps

import android.app.Application
import com.cattishapps.minka.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MinKaApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MinKaApp)
            modules(
                appModule
            )
        }
    }
}