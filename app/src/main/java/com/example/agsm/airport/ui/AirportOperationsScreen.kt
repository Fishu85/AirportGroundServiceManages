package com.example.agsm.airport.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.example.agsm.apron.ApronViewModel
import com.example.agsm.apron.ui.CreateApronDialog
import com.example.agsm.ui.theme.PrimaryBackground
import com.example.agsm.ui.theme.PrimaryForeground
import com.example.agsm.ui.theme.SecondaryForeground
import com.example.agsm.ui.theme.SecondaryText
import com.example.agsm.ui.theme.White
import com.example.agsm.user.Role
import com.example.agsm.user.UserViewModel

@Composable
fun AirportOperationsScreen(
    userVm: UserViewModel,
    airportVm: AirportViewModel,
    apronVm: ApronViewModel
) {
    val airportId = userVm.user?.airportId
    var showCreateApronDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(airportId) {
        if (airportId != null) {
            airportVm.getAirport(airportId)
        }
    }

    LaunchedEffect(Unit) {
        airportVm.loadAllAirports()
        userVm.loadUser()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBackground)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimaryForeground, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(7f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(airportVm.airport?.airportName ?: "",
                        color = White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier
                        .weight(2f),
                    horizontalAlignment = Alignment.End
                ) {
                    if(userVm.user?.role == Role.OPERATIONS_MANAGER) {
                        Button(
                            onClick = { showCreateApronDialog = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SecondaryForeground,
                                contentColor = White
                            )
                        ) {
                            Text(
                                "+",
                                color = White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(

            ) {

            }
        }
    }

    if (showCreateApronDialog) {
        Dialog(onDismissRequest = { showCreateApronDialog = false }) {
            CreateApronDialog(
                onConfirm = { apronNumber ->
                    apronVm.createApron(
                        apronNumber = apronNumber,
                        airportVm = airportVm
                    ) { ok, msg ->
                        if (ok) {
                            errorMessage = null
                            showCreateApronDialog = false
                        } else {
                            errorMessage = msg
                        }
                    }
                },
                onDismiss = {
                    errorMessage = null
                    showCreateApronDialog = false
                },
                errorMessage = errorMessage
            )
        }
    }
}