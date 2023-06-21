package com.example.sicredi.data

data class EventResponse(
    val id: String,
    val title: String,
    val people: List<String>,
    val date: Long,
    val description: String,
    val image: String,
    val longitude: Double,
    val latitude: Double,
    val price: Double
)
data class CheckInResponse(
    val code: String
)