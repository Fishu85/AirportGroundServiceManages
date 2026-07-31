package com.example.agsm.stand

import com.example.agsm.apron.Apron
import com.example.agsm.flight.AircraftCategory
import com.example.agsm.flight.Flight

data class Stand(
    val standId: String = "",
    val standNumber: String = "",
    val flight: Flight? = null,
    val apron: Apron = Apron(),
    val categories: List<AircraftCategory> = emptyList()
)