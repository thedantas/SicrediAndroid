package com.example.sicredi.model

import java.io.Serializable

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val date: Long,
    val latitude: Double,
    val longitude: Double,
    val price: Double
) : Serializable