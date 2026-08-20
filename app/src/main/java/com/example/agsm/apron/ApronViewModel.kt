package com.example.agsm.apron

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.agsm.airport.AirportViewModel
import com.example.agsm.stand.StandRepository

class ApronViewModel(
    private val apronRepo: ApronRepository = ApronRepository()
) : ViewModel() {
    var error by mutableStateOf<String?>(null)
    var apron by mutableStateOf<Apron?>(null)
        private set
    var aprons by mutableStateOf<List<Apron>>(emptyList())

    fun createApron(
        airportVm: AirportViewModel,
        apronNumber: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        val airport = airportVm.airport ?: run {
            onResult(false, "Airport not loaded")
            return
        }

        if (apronNumber.isBlank()) {
            onResult(false, "Apron number cannot be empty")
            return
        }

        apronRepo.createApron(
            apronNumber = apronNumber,
            airport = airport
        ) { ok, msg, createdApron ->
            if (!ok || createdApron == null) {
                error = msg
                onResult(false, msg)
                return@createApron
            }

            apron = createdApron
            error = null
            apronRepo.addApronToAirport(
                airportId = airport.airportId,
                apron = createdApron
            ) { assignOk, assignMsg ->
                if (assignOk) {
                    airportVm.getAirport(airport.airportId)
                    loadApronsForAirport(airportVm.airport!!.airportId)
                    onResult(true, msg)
                } else {
                    error = assignMsg
                    onResult(false, assignMsg)
                }
            }
        }
    }

    fun loadApronsForAirport(
        airportId: String
    ) {
        apronRepo.getApronsForAirport(airportId) { list ->
            aprons = list
        }
    }

    fun getApron(
        apronId: String?
    ) {
        apronRepo.getApron(apronId) { loadedApron ->
            apron = loadedApron
        }
    }

    fun selectApron(
        apron: Apron
    ) {
        this.apron = apron
    }

    fun deleteApron(
        standRepo: StandRepository,
        apron: Apron,
        onResult: (Boolean, String?) -> Unit
    ) {
        standRepo.getStandsForApron(apron.apronId) { stands ->
            if (stands.isNotEmpty()) {
                onResult(false, "Cannot delete apron with assigned stands")
                return@getStandsForApron
            }
            apronRepo.deleteApron(apron.apronId) { ok, msg ->
                if (ok) {
                    aprons = aprons.filter { it.apronId != apron.apronId }
                    onResult(true, null)
                } else {
                    onResult(false, msg)
                }
            }
        }
    }

    fun updateApron(
        apronNumber: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        val currentApron = apron ?: run {
            onResult(false, "Apron not loaded")
            return
        }

        if (apronNumber.isBlank()) {
            onResult(false, "Apron name cannot be empty")
            return
        }

        apronRepo.updateApron(
            apronId = currentApron.apronId,
            apronNumber = apronNumber
        ) { ok, msg ->
            if (!ok) {
                onResult(false, msg)
                return@updateApron
            }

            val updated = currentApron.copy(apronNumber = apronNumber)
            apron = updated
            aprons = aprons.map {
                if (it.apronId == updated.apronId) updated else it
            }
            onResult(true, null)
        }
    }
}