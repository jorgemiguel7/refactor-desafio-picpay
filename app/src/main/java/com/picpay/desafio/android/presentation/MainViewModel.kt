package com.picpay.desafio.android.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _users by lazy { MutableLiveData<List<User>>() }
    val users: LiveData<List<User>> = _users

    private val _isLoading by lazy { MutableLiveData<Boolean>() }
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error by lazy { MutableLiveData<Boolean>() }
    val error: LiveData<Boolean> = _error

    fun fetchUsers() = viewModelScope.launch {
        _isLoading.postValue(true)

        userRepository.getUsers().let { result ->
            _isLoading.postValue(false)

            if (result.isNotEmpty()) {
                _users.postValue(result)
            } else {
                _error.postValue(true)
            }
        }
    }
}
