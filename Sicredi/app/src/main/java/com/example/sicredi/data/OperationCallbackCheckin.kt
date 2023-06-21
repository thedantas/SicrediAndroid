package com.example.sicredi.data

interface OperationCallbackCheckin<T> {
    fun onSuccess(data: T)
    fun onError(error: String?)
}