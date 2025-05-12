package com.picpay.desafio.android.core.error

sealed interface DataError : Error {
    sealed interface Remote : DataError {
        object REQUEST_TIMEOUT : Remote
        object NO_INTERNET : Remote
        object SERIALIZATION : Remote
        object SERVER : Remote
        object TOO_MANY_REQUESTS : Remote
        object UNKNOWN : Remote
    }

    sealed interface Local : DataError {
        object DiskError : Local
        object Unknown : Local
    }
}