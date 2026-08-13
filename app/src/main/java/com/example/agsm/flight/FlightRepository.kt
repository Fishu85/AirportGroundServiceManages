package com.example.agsm.flight

import com.example.agsm.stand.Stand
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class FlightRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    fun createFlight(
        aircraftModel: String,
        aircraftCategory: AircraftCategory,
        airline: String,
        registrationNumber: String,
        flightNumber: String,
        stand: Stand,
        onResult: (Boolean, String?, Flight?) -> Unit
    ) {
        val docRef = db.collection("flights").document()
        val flightId = docRef.id
        val flight = Flight(
            flightId = flightId,
            aircraftModel = aircraftModel,
            aircraftCategory = aircraftCategory,
            airline = airline,
            registrationNumber = registrationNumber,
            flightNumber = flightNumber,
            stand = stand,
            aircraftPosition = AircraftPosition.ARRIVAL
        )

        docRef.set(flight)
            .addOnSuccessListener {
                onResult(true, "Flight created", flight)
            }
            .addOnFailureListener { e ->
                onResult(false, e.message, null)
            }
    }

    fun addFlightToStand(
        standId: String?,
        flight: Flight?,
        onResult: (Boolean, String?) -> Unit
    ) {
        if (standId != null) {
            db.collection("stands")
                .document(standId)
                .update("flight", flight)
                .addOnSuccessListener {
                    onResult(true, null)
                }
                .addOnFailureListener { e ->
                    onResult(false, e.message)
                }
        }
    }

    fun getFlightForStand(
        standId: String,
        onResult: (Flight?) -> Unit
    ) {
        db.collection("flights")
            .whereEqualTo("stand.standId", standId)
            .get()
            .addOnSuccessListener { snap ->
                val flights = snap.documents.mapNotNull { it.toObject(Flight::class.java) }
                onResult(flights.firstOrNull())
            }
            .addOnFailureListener {
                onResult(null)
            }
    }

    fun getFlight(
        flightId: String?,
        onResult: (Flight?) -> Unit
    ) {
        if (flightId != null) {
            db.collection("flights")
                .document(flightId)
                .get()
                .addOnSuccessListener { snap ->
                    onResult(snap.toObject(Flight::class.java))
                }
        }
    }

    fun updateFlightPosition(
        flightId: String,
        newPosition: AircraftPosition,
        onResult: (Boolean) -> Unit
    ) {
        db.collection("flights")
            .document(flightId)
            .update("aircraftPosition", newPosition)
            .addOnSuccessListener {
                onResult(true)
            }
            .addOnFailureListener {
                onResult(false)
            }
    }

    fun addService(
        flightId: String,
        service: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        db.collection("flights")
            .document(flightId)
            .update("operations", FieldValue.arrayUnion(service))
            .addOnSuccessListener {
                onResult(true, null)
            }
            .addOnFailureListener { e ->
                onResult(false, e.message)
            }
    }

    fun deleteService(
        flightId: String,
        service: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        db.collection("flights")
            .document(flightId)
            .update("operations", FieldValue.arrayRemove(service))
            .addOnSuccessListener {
                onResult(true, null)
            }
            .addOnFailureListener { e ->
                onResult(false, e.message)
            }
    }

    fun deleteFlight(
        flightId: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        db.collection("flights")
            .document(flightId)
            .delete()
            .addOnSuccessListener {
                onResult(true, null)
            }
            .addOnFailureListener { e ->
                onResult(false, e.message)
            }
    }
}