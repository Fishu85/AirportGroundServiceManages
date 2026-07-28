package com.example.agsm.apron

import com.example.agsm.airport.Airport
import com.example.agsm.stand.Stand

data class Apron(
    val apronId: String = "",
    val apronNumber: String = "",
    val stands: List<Stand> = emptyList(),
    val airport: Airport = Airport()
)