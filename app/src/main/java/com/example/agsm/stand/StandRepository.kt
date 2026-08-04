package com.example.agsm.stand

import com.example.agsm.apron.Apron
import com.example.agsm.flight.AircraftCategory
import com.example.agsm.flight.AircraftPosition
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

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
                val list = snap.documents.mapNotNull { it.toObject(Stand::class.java) }
                onResult(list)
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
}