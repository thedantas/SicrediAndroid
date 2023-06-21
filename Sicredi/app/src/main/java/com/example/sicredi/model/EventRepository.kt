package com.example.sicredi.model

import com.example.sicredi.data.OperationCallback

class EventRepository(private val eventDataSource: EventDataSource) {

    fun fetchEvents(callback: OperationCallback<Event>) {
        eventDataSource.retrieveEvents(callback)
    }

    fun cancel() {
        eventDataSource.cancel()
    }
}