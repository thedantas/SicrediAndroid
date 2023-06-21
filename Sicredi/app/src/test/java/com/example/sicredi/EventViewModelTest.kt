package com.example.sicredi

import com.example.sicredi.data.OperationCallback
import com.example.sicredi.model.EventRepository
import com.example.sicredi.model.Event
import com.example.sicredi.viewmodel.EventViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.*

class EventViewModelTest {

    @Mock
    private lateinit var repository: EventRepository

    private lateinit var viewModel: EventViewModel

    @Before
    fun setup() {
        repository = mock(EventRepository::class.java)

        viewModel = EventViewModel(repository)
    }

    @Test
    fun loadEventsSuccess() {
        // Given
        val mockEvent = Event(
            id = "1",
            title = "Test Event",
            description = "This is a test event.",
            imageUrl = "http://example.com/image.jpg",
            date = 1632455687000L,
            latitude = 10.0,
            longitude = 10.0,
            price = 100.0
        )
        val events = listOf<Event>(mockEvent)
        doAnswer { invocation ->
            val callback = invocation.getArgument<OperationCallback<Event>>(0)
            callback.onSuccess(events)
            null
        }.`when`(repository).fetchEvents(any())

        // When
        viewModel.loadEvents()

        // Then
        assertFalse(viewModel.isViewLoading.value!!)
        assertEquals(events, viewModel.events.value)
        assertNull(viewModel.onMessageError.value)
    }

}
