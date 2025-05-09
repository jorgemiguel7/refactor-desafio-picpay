package com.picpay.desafio.android.core

sealed interface Result<out D, out E : Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : com.picpay.desafio.android.core.Error>(val error: E) :
        Result<Nothing, E>
}
