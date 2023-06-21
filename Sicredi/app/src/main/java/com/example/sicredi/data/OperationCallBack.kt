package com.example.sicredi.data

interface OperationCallback<T> {
    fun onSuccess(data: List<T>)
    fun onError(error: String?)
}