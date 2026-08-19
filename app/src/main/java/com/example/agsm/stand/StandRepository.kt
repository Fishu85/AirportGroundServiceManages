package com.example.agsm.stand

import com.example.agsm.apron.Apron
import com.example.agsm.flight.AircraftCategory
import com.example.agsm.flight.AircraftPosition
import com.example.agsm.flight.Flight
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class StandRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    fun createStand(
        standNumber: String,
        categories: List<AircraftCategory>,
        apron: Apron,
        onResult: (Boolean, String?, Stand?) -> Unit
    ) {
        val docRef = db.collection("stands").document()
        val standId = docRef.id
        val stand = Stand(
            standId = standId,
            standNumber = standNumber,
            flight = null,
            apron = apron,
            categories = categories
        )

        docRef.set(stand)
            .addOnSuccessListener {
                onResult(true, "Stand created", stand)
            }
            .addOnFailureListener { e ->
                onResult(false, e.message, null)
            }
    }

    fun addStandToApron(
        apronId: String?,
        stand: Stand?,
        onResult: (Boolean, String?) -> Unit
    ) {
        if (apronId != null) {
            db.collection("aprons")
                .document(apronId)
                .update("stands", FieldValue.arrayUnion(stand))
                .addOnSuccessListener {
                    onResult(true, null)
                }
                .addOnFailureListener { e ->
                    onResult(false, e.message)
                }
        }
    }

    fun getStandsForApron(
        apronId: String,
        onResult: (List<Stand>) -> Unit
    ) {
        db.collection("stands")
            .whereEqualTo("apron.apronId", apronId)
            .get()
            .addOnSuccessListener { snap ->
                val stands = snap.documents.mapNotNull { it.toObject(Stand::class.java) }
                if (stands.isEmpty()) {
                    onResult(emptyList())
                    return@addOnSuccessListener
                }
                val result = mutableListOf<Stand>()
                var loadedCount = 0
                stands.forEach { stand ->
                    db.collection("flights")
                        .whereEqualTo("stand.standId", stand.standId)
                        .get()
                        .addOnSuccessListener { flightSnap ->
                            val flight = flightSnap.documents
                                .firstOrNull()
                                ?.toObject(Flight::class.java)
                            val updatedStand = stand.copy(flight = flight)
                            result.add(updatedStand)
                            loadedCount++
                            if (loadedCount == stands.size) {
                                onResult(result)
                            }
                        }
                        .addOnFailureListener {
                            result.add(stand)
                            loadedCount++
                            if (loadedCount == stands.size) {
                                onResult(result)
                            }
                        }
                }
            }
    }

    fun getStand(
        standId: String?,
        onResult: (Stand?) -> Unit
    ) {
        if (standId != null) {
            db.collection("stands")
                .document(standId)
                .get()
                .addOnSuccessListener { snap ->
                    onResult(snap.toObject(Stand::class.java))
                }
        }
    }

    fun updateStandFlightPosition(
        standId: String,
        newPosition: AircraftPosition,
        onResult: (Boolean) -> Unit
    ) {
        db.collection("stands")
            .document(standId)
            .update("flight.aircraftPosition", newPosition)
            .addOnSuccessListener {
                onResult(true)
            }
            .addOnFailureListener {
                onResult(false)
            }
    }

    fun removeFlightFromStand(
        standId: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        db.collection("stands")
            .document(standId)
            .update("flight", null)
            .addOnSuccessListener {
                onResult(true, null)
            }
            .addOnFailureListener { e ->
                onResult(false, e.message)
            }
    }

    fun deleteStand(
        standId: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        db.collection("stands")
            .document(standId)
            .delete()
            .addOnSuccessListener {
                onResult(true, null)
            }
            .addOnFailureListener { e ->
                onResult(false, e.message)
            }
    }

    fun updateStand(
        standId: String,
        standNumber: String,
        categories: List<AircraftCategory>,
        onResult: (Boolean, String?) -> Unit
    ) {
        val updates = mapOf(
            "standNumber" to standNumber,
            "categories" to categories,
        )

        db.collection("stands")
            .document(standId)
            .update(updates)
            .addOnSuccessListener {
                onResult(true, null)
            }
            .addOnFailureListener { e ->
                onResult(false, e.message)
            }
    }
}