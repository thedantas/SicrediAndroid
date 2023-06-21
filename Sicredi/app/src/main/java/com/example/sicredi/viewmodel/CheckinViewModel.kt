package com.example.sicredi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sicredi.data.OperationCallbackCheckin
import com.example.sicredi.data.CheckInResponse
import com.example.sicredi.model.Checkin
import com.example.sicredi.model.CheckinRepository

class CheckinViewModel(private val repository: CheckinRepository) : ViewModel() {

    private val _checkin = MutableLiveData<CheckInResponse>()
    val checkin: LiveData<CheckInResponse> = _checkin

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    fun makeCheckin(checkin: Checkin) {
        _isViewLoading.value = true
        repository.makeCheckin(checkin, object : OperationCallbackCheckin<CheckInResponse> {
            override fun onError(error: String?) {
                _isViewLoading.value = false
                _onMessageError.value = error
            }

            override fun onSuccess(data: CheckInResponse) {
                _isViewLoading.value = false
                _checkin.value = data
            }
        })
    }
}
