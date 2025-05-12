package com.picpay.desafio.android.di

import com.picpay.desafio.android.presentation.contacts.ContactsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::ContactsViewModel)
}