package com.example.agsm.airport.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.agsm.airport.AirportViewModel
import com.example.agsm.ui.theme.PrimaryBackground
import com.example.agsm.ui.theme.PrimaryForeground
import com.example.agsm.ui.theme.SecondaryBackground
import com.example.agsm.ui.theme.SecondaryForeground
import com.example.agsm.ui.theme.White
import com.example.agsm.user.Role
import com.example.agsm.user.UserViewModel

@Composable
fun AirportListScreen(
    userVm: UserViewModel,
    airportVm: AirportViewModel
) {
    var showCreateAirportDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBackground)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryForeground, RoundedCornerShape(16.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Your airport",
                color = White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (userVm.user?.airportId == null && userVm.user?.role == Role.OPERATIONS_MANAGER) {
                Button(
                    onClick = { showCreateAirportDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SecondaryForeground
                    )
                ) {
                    Text("+",
                        fontWeight = FontWeight.Bold,
                        color = White,
                        fontSize = 24.sp)
                }
            }
        }
    }

    if (showCreateAirportDialog) {
        Dialog(onDismissRequest = {showCreateAirportDialog = false}) {
            CreateAirportDialog(
                onConfirm = { airportName, icao, iata, joinCode ->
                    airportVm.createAirport(
                        icao = icao,
                        iata = iata,
                        airportName = airportName,
                        joinCode = joinCode,
                        manager = userVm.user
                    ) { ok, msg ->
                        if (ok) {
                            errorMessage = null
                            showCreateAirportDialog = false
                            userVm.updateUserAirportId(airportVm.airport?.airportId)
                        } else {
                            errorMessage = msg
                        }
                    }
                },
                onDismiss = {
                    errorMessage = null
                    showCreateAirportDialog = false
                },
                errorMessage = errorMessage
            )
        }
    }
}