package com.example.sicredi.di

import com.example.sicredi.data.ApiClient
import com.example.sicredi.data.EventRemoteDataSource
import com.example.sicredi.model.EventDataSource
import com.example.sicredi.model.EventRepository
import com.example.sicredi.viewmodel.EventViewModelFactory

object Injection {

    private var eventDataSource: EventDataSource? = null
    private var eventRepository: EventRepository? = null
    private var eventViewModelFactory: EventViewModelFactory? = null

    private fun createEventDataSource(): EventDataSource {
        val dataSource = EventRemoteDataSource(ApiClient)
        eventDataSource = dataSource
        return dataSource
    }

    private fun createEventRepository(): EventRepository {
        val repository = EventRepository(provideDataSource())
        eventRepository = repository
        return repository
    }

    private fun createFactory(): EventViewModelFactory {
        val factory = EventViewModelFactory(providerRepository())
        eventViewModelFactory = factory
        return factory
    }

    private fun provideDataSource() = eventDataSource ?: createEventDataSource()
    private fun providerRepository() = eventRepository ?: createEventRepository()

    fun provideViewModelFactory() = eventViewModelFactory ?: createFactory()

    fun destroy() {
        eventDataSource = null
        eventRepository = null
        eventViewModelFactory = null
    }
}