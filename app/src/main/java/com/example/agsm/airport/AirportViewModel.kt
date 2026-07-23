package com.example.agsm.airport

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.agsm.user.User
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class AirportViewModel (
    private val airportRepo: AirportRepository = AirportRepository()
) : ViewModel() {
    var airport by mutableStateOf<Airport?>(null)
        private set
    var error by mutableStateOf<String?>(null)
    var airports by mutableStateOf<List<Airport>>(emptyList())
        private set

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

    fun updateAirport(
        airportId: String,
        icao: String,
        iata: String,
        airportName: String,
        joinCode: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        airportRepo.updateAirport(
            airportId = airportId,
            icao = icao,
            iata = iata,
            airportName = airportName,
            joinCode = joinCode
        ) { ok, msg ->
            if (ok) {
                airport = airport?.copy(
                    icao = icao,
                    iata = iata,
                    airportName = airportName,
                    joinCode = joinCode
                )
                error = null
                onResult(true, msg)
            } else {
                error = msg
                onResult(false, msg)
            }
        }
    }

    fun deleteAirport(
        airportId: String,
        password: String,
        user: User,
        onResult: (Boolean, String?) -> Unit
    ) {
        val auth = FirebaseAuth.getInstance()
        val email = user.email

        if (email == null) {
            onResult(false, "User email not found")
            return
        }

        val credential = EmailAuthProvider.getCredential(email, password)

        auth.currentUser?.reauthenticate(credential)
            ?.addOnSuccessListener {
                airportRepo.deleteAirport(airportId) { ok, msg ->
                    if (ok) {
                        airportRepo.assignAirportToUser(user.uid, null) { _, _ -> }
                        airport = null
                        onResult(true, "Airport deleted")
                    } else {
                        onResult(false, msg)
                    }
                }
            }
            ?.addOnFailureListener { e ->
                onResult(false, "Wrong password")
            }
    }

    fun loadAllAirports() {
        airportRepo.getAllAirports { list ->
            airports = list
        }
    }
}