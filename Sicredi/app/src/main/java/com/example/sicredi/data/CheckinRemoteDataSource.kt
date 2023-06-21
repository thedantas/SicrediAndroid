package com.example.sicredi.data

import com.example.sicredi.model.Checkin
import com.example.sicredi.model.CheckinDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckinRemoteDataSource(apiClient: ApiClient) : CheckinDataSource {

    private var call: Call<CheckInResponse>? = null
    private val service = apiClient.build()

    override fun makeCheckin(checkin: Checkin, callback: OperationCallbackCheckin<CheckInResponse>) {

        call = service?.checkin(checkin)
        call?.enqueue(object : Callback<CheckInResponse> {
            override fun onFailure(call: Call<CheckInResponse>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(
                call: Call<CheckInResponse>,
                response: Response<CheckInResponse>
            ) {
                response.body()?.let { checkInDTO ->
                    if (response.isSuccessful) {
                        callback.onSuccess(checkInDTO)
                    } else {
                        callback.onError("Check-in failed")
                    }
                } ?: callback.onError("Check-in failed")
            }
        })
    }

    override fun cancel() {
        call?.let {
            it.cancel()
        }
    }
}
