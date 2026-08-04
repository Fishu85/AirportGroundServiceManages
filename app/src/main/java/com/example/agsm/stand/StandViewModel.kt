package com.example.agsm.stand

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.agsm.apron.ApronViewModel
import com.example.agsm.flight.AircraftCategory

class StandViewModel(
    private val standRepo: StandRepository = StandRepository()
) : ViewModel() {
    var error by mutableStateOf<String?>(null)
    var stand by mutableStateOf<Stand?>(null)
        private set
    var stands by mutableStateOf<List<Stand?>>(emptyList())

    fun createStand(
        apronVm: ApronViewModel,
        standNumber: String,
        categories: List<AircraftCategory>,
        onResult: (Boolean, String?) -> Unit
    ) {
        val apron = apronVm.apron ?: run {
            onResult(false, "Apron not loaded")
            return
        }

        if (standNumber.isBlank()) {
            onResult(false, "Stand number cannot be empty")
            return
        }

        if (categories.isEmpty()) {
            onResult(false, "At least one category must be selected")
            return
        }

        standRepo.createStand(
            standNumber = standNumber,
            categories = categories,
            apron = apron
        ) { ok, msg, createdStand ->
            if (!ok || createdStand == null) {
                error = msg
                onResult(false, msg)
                return@createStand
            }
            stand = createdStand
            error = null
            standRepo.addStandToApron(
                apronId = apron.apronId,
                stand = createdStand
            ) { assignOk, assignMsg ->
                if (assignOk) {
                    apronVm.getApron(apron.apronId)
                    loadStandsForApron(apron.apronId)
                    onResult(true, msg)
                } else {
                    error = assignMsg
                    onResult(false, assignMsg)
                }
            }
        }
    }

    fun loadStandsForApron(
        apronId: String
    ) {
        standRepo.getStandsForApron(apronId) { list ->
            stands = list
        }
    }

    fun getStand(
        standId: String?
    ) {
        standRepo.getStand(standId) { loadedStand ->
            stand = loadedStand
        }
    }

    fun updateStand(newStand: Stand?) {
        stand = newStand
    }
}