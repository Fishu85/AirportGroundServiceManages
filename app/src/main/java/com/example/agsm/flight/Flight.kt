package com.example.agsm.flight

import com.example.agsm.stand.Stand

data class Flight(
    val flightId: String = "",
    val aircraftModel: String = "",
    val aircraftCategory: AircraftCategory? = null,
    val airline: String = "",
    val registrationNumber: String = "",
    val flightNumber: String = "",
    val stand: Stand? = null,
    val operations: List<String> = emptyList(),
    val aircraftPosition: AircraftPosition = AircraftPosition.ARRIVAL
)