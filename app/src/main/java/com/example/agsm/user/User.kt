package com.example.agsm.user

data class User(
    val uid: String = "",
    val email: String = "",
    val name: String = "",
    val role: Role = Role.RAMP_SUPERVISOR,
    val airportId: String? = null
)