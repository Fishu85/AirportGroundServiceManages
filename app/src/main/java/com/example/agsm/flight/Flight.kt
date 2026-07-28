package com.example.agsm.flight

import com.example.agsm.stand.Stand

data class Flight(
    val flightId: String,
    val aircraftModel: String,
    val aircraftCategory: AircraftCategory,
    val airline: String,
    val registrationNumber: String,
    val flightNumber: String,
    val stand: Stand,
    val operations: List<Operations> = emptyList(),
    val aircraftPosition: AircraftPosition
)