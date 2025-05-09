package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.data.di.networkModule
import com.picpay.desafio.android.data.di.repositoryModule
import com.picpay.desafio.android.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(
                viewModelModule,
                repositoryModule,
                networkModule
            )
        }
    }
}