package com.example.agsm.airport

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.agsm.user.User

class AirportViewModel (
    private val airportRepo: AirportRepository = AirportRepository()
) : ViewModel() {
    var airport by mutableStateOf<Airport?>(null)
        private set
    var error by mutableStateOf<String?>(null)

    fun createAirport(
        icao: String,
        iata: String,
        airportName: String,
        joinCode: String,
        manager: User?,
        onResult: (Boolean, String?) -> Unit
    ) {
        if (icao.isBlank()) {
            onResult(false, "ICAO code cannot be empty")
            return
        }
        if (iata.isBlank()) {
            onResult(false, "IATA code cannot be empty")
            return
        }
        if (airportName.isBlank()) {
            onResult(false, "Airport name cannot be empty")
            return
        }

        if (joinCode.isBlank()) {
            onResult(false, "Join code cannot be empty")
            return
        }

        airportRepo.createAirport(
            icao = icao,
            iata = iata,
            airportName = airportName,
            joinCode = joinCode,
            manager = manager
        ) {ok, msg, createdAirport ->
            if (ok) {
                airport = createdAirport
                error = null
                manager?.let { user ->
                    airportRepo.assignAirportToUser(
                        userId = user.uid,
                        airportId = createdAirport!!.airportId
                    ) { userOk, userMsg ->
                        if (!userOk) {
                            error = userMsg
                        }
                    }
                }
                onResult(true, msg)
            } else {
                error = msg
                onResult(false, msg)
            }
        }

    }

    fun getAirport(
        airportId: String?
    ) {
        airportRepo.getAirport(airportId) { loadedAirport ->
            airport = loadedAirport
        }
    }
}