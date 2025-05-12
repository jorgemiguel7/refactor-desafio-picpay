package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.FakeUserRepository
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.presentation.contacts.ContactsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

object TestModules {
    private val fakeUserRepository = FakeUserRepository()

    val testModule = module {
        single<UserRepository> { fakeUserRepository }

        viewModel { ContactsViewModel(get()) }
    }

    fun loadModules() {
        loadKoinModules(testModule)
    }

    fun unloadModules() {
        unloadKoinModules(testModule)
    }

    fun getFakeUserRepository(): FakeUserRepository = fakeUserRepository
}