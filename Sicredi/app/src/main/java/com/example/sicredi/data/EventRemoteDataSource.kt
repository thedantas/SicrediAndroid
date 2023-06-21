package com.example.sicredi.data

import com.example.sicredi.model.Event
import com.example.sicredi.model.EventDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventRemoteDataSource(apiClient: ApiClient) : EventDataSource {

    private var call: Call<List<EventResponse>>? = null
    private val service = apiClient.build()

    override fun retrieveEvents(callback: OperationCallback<Event>) {

        call = service?.events()
        call?.enqueue(object : Callback<List<EventResponse>> {
            override fun onFailure(call: Call<List<EventResponse>>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(
                call: Call<List<EventResponse>>,
                response: Response<List<EventResponse>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { eventResponseList ->
                        if (eventResponseList.isNotEmpty()) {
                            val events = eventResponseList.map { eventResponse ->
                                Event(

                                    date = eventResponse.date,
                                    description = eventResponse.description,
                                    longitude = eventResponse.longitude,
                                    latitude = eventResponse.latitude,
                                    price = eventResponse.price,
                                    title = eventResponse.title,
                                    id = eventResponse.id,
                                    imageUrl = eventResponse.image
                                )
                            }
                            callback.onSuccess(events)
                        } else {
                            callback.onError("Empty event list")
                        }
                    }
                } else {
                    callback.onError("Failed to retrieve events")
                }
            }
        })
    }

    override fun cancel() {
        call?.let {
            it.cancel()
        }
    }
}
