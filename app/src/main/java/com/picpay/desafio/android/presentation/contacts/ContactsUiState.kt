package com.picpay.desafio.android.presentation.contacts

import com.picpay.desafio.android.domain.model.User

data class ContactsUiState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList()
)
