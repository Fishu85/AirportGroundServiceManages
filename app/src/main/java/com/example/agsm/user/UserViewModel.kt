package com.example.agsm.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.agsm.auth.AuthRepository

class UserViewModel(
    private val authRepo: AuthRepository = AuthRepository(),
    private val userRepo: UserRepository = UserRepository()
) : ViewModel() {
    var user by mutableStateOf<User?>(null)
        private set

    var error by mutableStateOf<String?>(null)

    fun loadUser() {
        val uid = authRepo.currentUser()?.uid ?: return

        userRepo.getUser(uid) { loadedUser ->
            user = loadedUser
        }
    }

    fun clear() {
        user = null
    }

    fun updateUser(
        name: String,
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        val uid = authRepo.currentUser()?.uid
        if (uid == null) {
            onResult(false, "Not logged in")
            return
        }
        if (name.isBlank()) {
            onResult(false, "Name cannot be empty")
            return
        }
        if (email.isBlank()) {
            onResult(false, "Email cannot be empty")
        }

        userRepo.updateUser(uid, name, email, password) { success, msg ->
            if (success) {
                user = user?.copy(
                    name = name,
                    email = email
                )
                error = null
                onResult(true, msg)
            } else {
                error = msg
                onResult(false, msg)
            }
        }
    }
}