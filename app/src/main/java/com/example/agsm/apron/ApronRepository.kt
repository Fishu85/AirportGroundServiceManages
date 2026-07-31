package com.example.agsm.apron

import com.example.agsm.airport.Airport
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class ApronRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    fun createApron(
        apronNumber: String,
        airport: Airport,
        onResult: (Boolean, String?, Apron?) -> Unit
    ) {
        val docRef = db.collection("aprons").document()
        val apronId = docRef.id
        val apron = Apron(
            apronId = apronId,
            apronNumber = apronNumber,
            stands = emptyList(),
            airport = airport
        )

        docRef.set(apron)
            .addOnSuccessListener {
                onResult(true, "Apron created", apron)
            }
            .addOnFailureListener { e ->
                onResult(false, e.message, null)
            }
    }

    fun addApronToAirport(
        airportId: String?,
        apron: Apron?,
        onResult: (Boolean, String?) -> Unit
    ) {
        if (airportId != null) {
            db.collection("airports")
                .document(airportId)
                .update("aprons", FieldValue.arrayUnion(apron))
                .addOnSuccessListener {
                    onResult(true, null)
                }
                .addOnFailureListener { e ->
                    onResult(false, e.message)
                }
        }
    }

    fun getApronsForAirport(
        airportId: String,
        onResult: (List<Apron>) -> Unit
    ) {
        db.collection("aprons")
            .whereEqualTo("airport.airportId", airportId)
            .get()
            .addOnSuccessListener { snap ->
                val list = snap.documents.mapNotNull { it.toObject(Apron::class.java) }
                onResult(list)
            }
    }

    fun getApron(
        apronId: String?,
        onResult: (Apron?) -> Unit
    ) {
        if (apronId != null) {
            db.collection("aprons")
                .document(apronId)
                .get()
                .addOnSuccessListener { snap ->
                    onResult(snap.toObject(Apron::class.java))
                }
        }
    }
}