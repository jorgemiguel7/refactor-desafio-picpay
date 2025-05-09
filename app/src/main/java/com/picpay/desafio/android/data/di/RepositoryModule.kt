package com.picpay.desafio.android.data.di

import com.picpay.desafio.android.data.UserRepositoryImpl
import com.picpay.desafio.android.domain.repository.UserRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::UserRepositoryImpl).bind<UserRepository>()
}