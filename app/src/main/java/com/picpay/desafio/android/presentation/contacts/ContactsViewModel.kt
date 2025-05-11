package com.picpay.desafio.android.presentation.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.core.result.onResult
import com.picpay.desafio.android.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactsViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ContactsUiState())
    val uiState = _uiState
        .onStart {
            fetchUsers()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ContactsUiState()
        )


    private val _effects = MutableSharedFlow<ContactsEffect>()
    val effects = _effects.asSharedFlow()

    private fun fetchUsers() {
        _uiState.update { it.copy(isLoading = true) }

        userRepository.getUsers()
            .onEach { result ->
                result.onResult(
                    onSuccess = { users ->
                        _uiState.update { it.copy(users = users, isLoading = false) }
                    },
                    onError = {
                        _uiState.update { it.copy(isLoading = false) }
                        emitEffect(ContactsEffect.ShowToastError)
                    }
                )
            }
            .launchIn(viewModelScope)
    }

    private fun emitEffect(effect: ContactsEffect) = viewModelScope.launch {
        _effects.emit(effect)
    }
}