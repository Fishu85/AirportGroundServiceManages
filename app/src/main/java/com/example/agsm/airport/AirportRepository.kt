package com.example.agsm.airport

import com.example.agsm.user.User
import com.google.firebase.firestore.FirebaseFirestore

class AirportRepository (
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    fun createAirport(
        icao: String,
        iata: String,
        airportName: String,
        joinCode: String,
        manager: User?,
        onResult: (Boolean, String?, Airport?) -> Unit
    ) {
        val docRef = db.collection("airports").document()
        val airportId = docRef.id
        val airport = Airport(
            airportId = airportId,
            icao = icao,
            iata = iata,
            airportName = airportName,
            joinCode = joinCode,
            manager = manager
        )

        docRef.set(airport)
            .addOnSuccessListener {
                onResult(true, "Airport created", airport)
            }
            .addOnFailureListener { e ->
                onResult(false, e.message, null)
            }
    }

    fun getAirport(
        airportId: String?,
        onResult: (Airport?) -> Unit
    ) {
        if (airportId != null) {
            db.collection("airports")
                .document(airportId)
                .get()
                .addOnSuccessListener { snap ->
                    onResult(snap.toObject(Airport::class.java))
                }
        }
    }

    fun assignAirportToUser(userId: String, airportId: String, onResult: (Boolean, String?) -> Unit) {
        db.collection("users")
            .document(userId)
            .update("airportId", airportId)
            .addOnSuccessListener {
                onResult(true, null)
            }
            .addOnFailureListener { e ->
                onResult(false, e.message)
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
        val updates = mapOf(
            "icao" to icao,
            "iata" to iata,
            "airportName" to airportName,
            "joinCode" to joinCode
        )

        db.collection("airports")
            .document(airportId)
            .update(updates)
            .addOnSuccessListener {
                onResult(true, "Airport updated")
            }
            .addOnFailureListener { e ->
                onResult(false, e.message)
            }
    }
}