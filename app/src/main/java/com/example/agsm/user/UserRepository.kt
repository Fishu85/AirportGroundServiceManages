package com.example.agsm.user

import androidx.compose.animation.core.snap
import com.google.firebase.firestore.FirebaseFirestore

class UserRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    fun createUser(user: User) {
        db.collection("users")
            .document(user.uid)
            .set(user)
    }

    fun getUser(uid: String, onResult: (User?) -> Unit) {
        db.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { snap ->
                onResult(snap.toObject(User::class.java))
            }
    }
}