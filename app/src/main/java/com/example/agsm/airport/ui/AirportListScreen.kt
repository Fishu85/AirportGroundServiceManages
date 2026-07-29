package com.example.agsm.airport.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.agsm.ui.theme.SecondaryForeground
import com.example.agsm.ui.theme.White
import com.example.agsm.user.Role
import com.example.agsm.user.UserViewModel
import androidx.compose.foundation.lazy.items
import com.example.agsm.airport.Airport
import com.example.agsm.ui.theme.SecondaryBackground

@Composable
fun AirportListScreen(
    userVm: UserViewModel,
    airportVm: AirportViewModel
) {
    var showCreateAirportDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val airportId = userVm.user?.airportId

    LaunchedEffect(airportId) {
        if (airportId != null) {
            airportVm.getAirport(airportId)
        }
    }

    LaunchedEffect(Unit) {
        airportVm.loadAllAirports()
        userVm.loadUser()
    }

    val airports = airportVm.airports

    var showJoinDialog by remember { mutableStateOf(false) }
    var selectedAirport by remember { mutableStateOf<Airport?>(null) }
    var joinError by remember { mutableStateOf<String?>(null) }

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
            } else if (userVm.user?.airportId == null && userVm.user?.role == Role.RAMP_SUPERVISOR) {
                Text("You are not assigned to any airport.",
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp)
            } else {
                val airport = airportVm.airport

                if (airport != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(airport.airportName,
                            color = White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp)

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("${airport.icao}/${airport.iata}",
                            color = White,
                            fontSize = 16.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimaryForeground, RoundedCornerShape(16.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("List of airports",
                color = White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp)

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SecondaryBackground, RoundedCornerShape(16.dp))
            ) {
                items(airports) { airport ->
                    AirportTile(airport, userVm) {
                        selectedAirport = airport
                        showJoinDialog = true
                    }
                    Spacer(modifier = Modifier.height(8.dp))
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
                            userVm.updateUserAirportId(airportVm.airport?.airportId) { ok ->
                                if (ok) {
                                    showCreateAirportDialog = false
                                }
                            }
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

    if (showJoinDialog && selectedAirport != null) {
        Dialog(onDismissRequest = { showJoinDialog = false }) {
            JoinAirportDialog(
                onConfirm = { joinCode ->
                    airportVm.joinAirport(selectedAirport!!, joinCode, userVm.user!!) { ok, msg ->
                        if (ok) {
                            joinError = null
                            showJoinDialog = false
                            userVm.updateUserAirportId(selectedAirport!!.airportId) { ok ->
                                if (ok) {
                                    showJoinDialog = false
                                }
                            }
                            userVm.loadUser()
                        } else {
                            joinError = msg
                        }
                    }
                },
                onDismiss = { showJoinDialog = false },
                errorMessage = joinError,
                airportVm = airportVm
            )
        }
    }
}