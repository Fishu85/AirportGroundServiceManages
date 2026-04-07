package com.example.agsm.auth

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlin.concurrent.timerTask

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    fun register(
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    auth.currentUser?.sendEmailVerification()
                        ?.addOnCompleteListener { verifyTask ->
                            if (verifyTask.isSuccessful) {
                                onResult(true, null)
                            } else {
                                onResult(false, verifyTask.exception?.message)
                            }
                        }
                else
                    onResult(false, task.exception?.message)
            }
    }

    fun login(
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    onResult(true, null)
                else
                    onResult(false, task.exception?.message)
            }
    }

    fun resetPassword(email: String, onResult: (Boolean, String?) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun logout() {
        auth.signOut()
    }

    fun currentUser() = auth.currentUser

    fun deleteAccount(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        val user = auth.currentUser ?: return onResult(false, "User not logged in")

        val credential = EmailAuthProvider.getCredential(email, password)
        user.reauthenticate(credential).addOnCompleteListener { reauthTask ->
            if (!reauthTask.isSuccessful) {
                onResult(false, reauthTask.exception?.message)
                return@addOnCompleteListener
            }

            user.delete().addOnCompleteListener { deleteTask ->
                if (deleteTask.isSuccessful)
                    onResult(true, null)
                else
                    onResult(false, deleteTask.exception?.message)
            }
        }
    }

    fun reauthenticate(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        val user = auth.currentUser ?: return onResult(false, "User not logged in")

        val credential = EmailAuthProvider.getCredential(email, password)

        user.reauthenticate(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    onResult(true, null)
                else
                    onResult(false, task.exception?.message)
            }
    }
}