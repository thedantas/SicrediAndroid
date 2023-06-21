package com.example.sicredi.model

import com.example.sicredi.data.CheckInResponse
import com.example.sicredi.data.OperationCallbackCheckin

interface CheckinDataSource {

    fun makeCheckin(checkin: Checkin, callback: OperationCallbackCheckin<CheckInResponse>)

    fun cancel()
}
