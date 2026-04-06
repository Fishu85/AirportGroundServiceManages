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

    fun loadUser() {
        val uid = authRepo.currentUser()?.uid ?: return

        userRepo.getUser(uid) { loadedUser ->
            user = loadedUser
        }
    }

    fun clear() {
        user = null
    }
}