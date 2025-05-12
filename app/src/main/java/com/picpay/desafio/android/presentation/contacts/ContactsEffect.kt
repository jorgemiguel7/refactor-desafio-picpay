package com.picpay.desafio.android.presentation.contacts

sealed class ContactsEffect {
    object ShowToastError : ContactsEffect()
}