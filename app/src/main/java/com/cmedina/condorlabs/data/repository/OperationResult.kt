package com.cmedina.condorlabs.data.repository

sealed class OperationResult<out T> {
    class Success<out T : Any?>(val data: T) : OperationResult<T>()
    data class Error(val exception: Throwable) : OperationResult<Nothing>()
}