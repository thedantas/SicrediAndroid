package com.example.sicredi.model

import com.example.sicredi.data.OperationCallback

interface EventDataSource {
    fun retrieveEvents(callback: OperationCallback<Event>)
    fun cancel()
}