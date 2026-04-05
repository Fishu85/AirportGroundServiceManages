package com.example.agsm.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agsm.user.Role
import com.example.agsm.user.User
import com.example.agsm.user.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepo: AuthRepository = AuthRepository(),
    private val userRepo: UserRepository = UserRepository()
): ViewModel() {

    var loading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)
    var loggedIn by mutableStateOf(false)
    var info by mutableStateOf<String?>(null)

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun register(
        email: String,
        password: String,
        confirmPassword: String,
        name: String,
        role: Role
    ) {
        viewModelScope.launch {
            if (name.isBlank()) {
                error = "Name and surname is empty"
                return@launch
            }

            if (email.isBlank()) {
                error = "E-mail is empty"
                return@launch
            }

            if (!isEmailValid(email)) {
                error = "E-mail address is incorrect"
                return@launch
            }

            if (password.length < 6) {
                error = "Password has less than 6 characters"
                return@launch
            }

            if (password != confirmPassword) {
                error = "Passwords are not the same"
                return@launch
            }

            loading = true
            authRepo.register(email, password) { success, msg ->
                loading = false
                if (!success) {
                    error = msg
                    return@register
                }

                val uid = authRepo.currentUser()!!.uid

                val user = User(
                    uid = uid,
                    email = email,
                    name = name,
                    role = role,
                    airportId = null
                )

                userRepo.createUser(user)
                error = null
                info = "Verification email sent. Check your inbox."
            }
        }
    }

    fun login(
        email: String,
        password: String
    ) {
        if (email.isBlank()) {
            error = "Email is empty"
            return
        }

        if (password.isBlank()) {
            error = "Password is empty"
            return
        }

        loading = true
        authRepo.login(email, password) { success, msg ->
            loading = false

            if (!success) {
                error = msg
                return@login
            }

            authRepo.currentUser()?.reload()?.addOnCompleteListener {
                val user = authRepo.currentUser()

                if (user != null && !user.isEmailVerified) {
                    error = "Confirm your email before logging in."
                    return@addOnCompleteListener
                }

                loggedIn = true
            }
        }
    }

    fun resetPassword(email: String) {
        if (email.isBlank()) {
            error = "Enter your email first"
            return
        }

        loading = true
        authRepo.resetPassword(email) { success, msg ->
            loading = false
            if (success) {
                error = null
                info = "Password reset email sent"
            } else {
                error = msg
            }
        }
    }
}