package com.example.agsm.user

import androidx.compose.animation.core.snap
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
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

    fun deleteUser(uid: String, onResult: (Boolean) -> Unit) {
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(uid)
            .delete()
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    fun updateUser(
        uid: String,
        name: String,
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        val user = FirebaseAuth.getInstance().currentUser
        val oldEmail = user?.email

        if (oldEmail == email) {
            updateFirestoreUser(uid, name, email, onResult)
            return
        }

        if (password.isBlank()) {
            onResult(false, "Password is required to change email")
            return
        }

        updateUserEmailAuth(email, password) { successAuth, msgAuth ->
            if (!successAuth) {
                onResult(false, msgAuth)
                return@updateUserEmailAuth
            }

            updateFirestoreUser(uid, name, email) { successFs, msgFs ->
                if (successFs) {
                    onResult(true, msgAuth)
                } else {
                    onResult(false, msgFs)
                }
            }
        }
    }

    fun updateFirestoreUser(
        uid: String,
        name: String,
        email: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        val updates = mapOf(
            "name" to name,
            "email" to email
        )

        db.collection("users")
            .document(uid)
            .update(updates)
            .addOnSuccessListener { onResult(true, "Profile updated") }
            .addOnFailureListener { e -> onResult(false, e.message) }
    }

    fun updateUserEmailAuth(
        newEmail: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            onResult(false, "Not logged in")
            return
        }

        val oldEmail = user.email ?: return onResult(false, "No email found")

        val credential = EmailAuthProvider.getCredential(oldEmail, password)
        user.reauthenticate(credential)
            .addOnCompleteListener { reauth ->
                if (!reauth.isSuccessful) {
                    onResult(false, reauth.exception?.message)
                    return@addOnCompleteListener
                }

                user.verifyBeforeUpdateEmail(newEmail)
                    .addOnSuccessListener {
                        onResult(true, "Verification email sent to $newEmail")
                    }
                    .addOnFailureListener { e ->
                        onResult(false, e.message)
                    }
            }
    }
}