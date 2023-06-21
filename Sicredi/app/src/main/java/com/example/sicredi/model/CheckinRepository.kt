package com.example.sicredi.model

import com.example.sicredi.data.CheckInResponse
import com.example.sicredi.data.OperationCallbackCheckin

class CheckinRepository(private val checkinDataSource: CheckinDataSource) {

    fun makeCheckin(checkin: Checkin, callback: OperationCallbackCheckin<CheckInResponse>) {
        checkinDataSource.makeCheckin(checkin, callback)
    }
}
