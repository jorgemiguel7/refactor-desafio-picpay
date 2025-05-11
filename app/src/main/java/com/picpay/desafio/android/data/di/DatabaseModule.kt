package com.picpay.desafio.android.data.di

import androidx.room.Room
import com.picpay.desafio.android.data.local.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
    single { get<AppDatabase>().userDao() }
}