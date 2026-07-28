package com.example.agsm.airport

import com.example.agsm.apron.Apron
import com.example.agsm.user.User

data class Airport (
    val airportId: String = "",
    val icao: String = "",
    val iata: String = "",
    val airportName: String = "",
    val joinCode: String = "",
    val manager: User? = null,
    val aprons: List<Apron> = emptyList()
)