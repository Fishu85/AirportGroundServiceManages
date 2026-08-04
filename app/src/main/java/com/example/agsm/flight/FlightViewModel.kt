package com.example.agsm.flight

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.agsm.stand.StandViewModel

class FlightViewModel(
    private val flightRepo: FlightRepository = FlightRepository()
) : ViewModel() {
    var error by mutableStateOf<String?>(null)
    var flight by mutableStateOf<Flight?>(null)
        private set

    fun createFlight(
        standVm: StandViewModel,
        aircraftModel: String,
        aircraftCategory: AircraftCategory,
        airline: String,
        registrationNumber: String,
        flightNumber: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        val stand = standVm.stand ?: run {
            onResult(false, "Stand not loaded")
            return
        }

        if (aircraftModel.isBlank()) {
            onResult(false, "Aircraft model cannot be empty")
            return
        }

        if (registrationNumber.isBlank()) {
            onResult(false, "Registration number cannot be empty")
            return
        }

        if (!stand.categories.contains(aircraftCategory)) {
            onResult(false, "This stand doesn't accept chosen aircraft category")
            return
        }

        flightRepo.createFlight(
            aircraftModel = aircraftModel,
            aircraftCategory = aircraftCategory,
            airline = airline,
            registrationNumber = registrationNumber,
            flightNumber = flightNumber,
            stand = stand
        ) { ok, msg, createdFlight ->
            if (!ok || createdFlight == null) {
                error = msg
                onResult(false, msg)
                return@createFlight
            }
            flight = createdFlight
            error = null
            flightRepo.addFlightToStand(
                standId = stand.standId,
                flight = createdFlight
            ) { assignOk, assignMsg ->
                if (assignOk) {
                    standVm.getStand(stand.standId)
                    loadFlightForStand(stand.standId)
                    onResult(true, msg)
                } else {
                    error = assignMsg
                    onResult(false, assignMsg)
                }
            }
        }
    }

    fun loadFlightForStand(
        standId: String
    ) {
        flightRepo.getFlightForStand(standId) { loadedFlight ->
            flight = loadedFlight
        }
    }
}