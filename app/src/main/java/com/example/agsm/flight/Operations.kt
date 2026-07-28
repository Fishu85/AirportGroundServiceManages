package com.example.agsm.flight

enum class Operations(
    val label: String
) {
    NO_OPERATIONS("No operations"),
    GPU_ATTACHED("GPU attached"),
    BOARDING("Boarding"),
    UNBOARDING("Unboarding"),
    LOADING_CARGO("Loading cargo"),
    UNLOADING_CARGO("Unloading cargo"),
    REFUELING("Refueling"),
    CLEANING("Cleaning"),
    CATERING_EXCHANGE("Catering exchange"),
    EMPTYING_TOILETS("Emptying toilets"),
    MAINTENANCE("Maintenance"),
    PUSHBACK("Pushback"),
    DE_ICING("De-icing")
}