package com.picpay.desafio.android.core.error

import android.database.SQLException
import com.google.gson.JsonParseException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Exception.toNetworkException(): DataError.Remote =
    when (this) {
        is SocketTimeoutException -> DataError.Remote.REQUEST_TIMEOUT
        is UnknownHostException, is ConnectException -> DataError.Remote.NO_INTERNET
        is JsonParseException -> DataError.Remote.SERIALIZATION
        is retrofit2.HttpException -> {
            when (this.code()) {
                500, 503 -> DataError.Remote.SERVER
                429 -> DataError.Remote.TOO_MANY_REQUESTS
                else -> DataError.Remote.UNKNOWN
            }
        }

        else -> DataError.Remote.UNKNOWN
    }

fun Exception.toLocalException(): DataError.Local =
    when (this) {
        is SQLException -> DataError.Local.DiskError
        else -> DataError.Local.Unknown
    }