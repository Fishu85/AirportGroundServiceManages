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

    fun assignAirportToUser(userId: String, airportId: String?, onResult: (Boolean, String?) -> Unit) {
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

    fun deleteAirport(
        airportId: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        db.collection("airports")
            .document(airportId)
            .delete()
            .addOnSuccessListener {
                onResult(true, "airport deleted")
            }
            .addOnFailureListener { e ->
                onResult(false, e.message)
            }
    }

    fun getAllAirports(
        onResult: (List<Airport>) -> Unit
    ) {
        db.collection("airports")
            .get()
            .addOnSuccessListener { snap ->
                val airports = snap.documents.mapNotNull { it.toObject(Airport::class.java) }
                onResult(airports)
            }
    }

    fun clearAirportIdForAllUsers(
        airportId: String,
        onResult: (Boolean) -> Unit
    ) {
        db.collection("users")
            .whereEqualTo("airportId", airportId)
            .get()
            .addOnSuccessListener { snap ->
                if (snap.isEmpty) {
                    onResult(true)
                    return@addOnSuccessListener
                }

                var successCount = 0
                var failureCount = 0
                val total = snap.size()

                snap.documents.forEach { doc ->
                    val userId = doc.getString("uid") ?: doc.id
                    assignAirportToUser(userId, null) { ok, _ ->
                        if (ok) successCount++ else failureCount++

                        if (successCount + failureCount == total) {
                            onResult(failureCount == 0)
                        }
                    }
                }
            }
            .addOnFailureListener {
                onResult(false)
            }
    }
}