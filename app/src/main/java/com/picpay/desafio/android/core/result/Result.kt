package com.picpay.desafio.android.core.result

import com.picpay.desafio.android.core.error.Error

sealed interface Result<out D, out E : Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : com.picpay.desafio.android.core.error.Error>(val error: E) :
        Result<Nothing, E>
}

inline fun <D, E : Error> Result<D, E>.onResult(onSuccess: (D) -> Unit, onError: (E) -> Unit) {
    when (this) {
        is Result.Success -> onSuccess(data)
        is Result.Error -> onError(error)
    }
}
