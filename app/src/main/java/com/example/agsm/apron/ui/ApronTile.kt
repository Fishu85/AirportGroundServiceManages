package com.example.agsm.apron.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.agsm.R
import com.example.agsm.apron.Apron
import com.example.agsm.apron.ApronViewModel
import com.example.agsm.flight.AircraftCategory
import com.example.agsm.flight.FlightViewModel
import com.example.agsm.stand.StandViewModel
import com.example.agsm.stand.ui.CreateStandDialog
import com.example.agsm.stand.ui.StandTile
import com.example.agsm.ui.theme.SecondaryBackground
import com.example.agsm.ui.theme.White
import com.example.agsm.user.Role
import com.example.agsm.user.UserViewModel

@Composable
fun ApronTile(
    apron: Apron,
    userVm: UserViewModel,
    standVm: StandViewModel,
    apronVm: ApronViewModel,
    flightVm: FlightViewModel
) {
    var isApronTileExpanded by remember { mutableStateOf(false) }
    var showCreateStandDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val stands = standVm.stands
        .filter { it?.apron?.apronId == apron.apronId }
        .sortedBy { it?.standNumber }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .background(SecondaryBackground, RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(10f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(apron.apronNumber,
                    color = White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                if (userVm.user?.role == Role.OPERATIONS_MANAGER) {
                    Text("+",
                        color = White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .clickable {
                                apronVm.selectApron(apron)
                                showCreateStandDialog = true
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                Image(
                    painter = painterResource(
                        if (!isApronTileExpanded) {
                            R.drawable.baseline_arrow_drop_up_24
                        } else {
                            R.drawable.outline_arrow_drop_down_24
                        }
                    ),
                    contentDescription = "expand apron tile",
                    colorFilter = ColorFilter.tint(White),
                    modifier = Modifier
                        .clickable {
                            if (!isApronTileExpanded) {
                                standVm.loadStandsForApron(apron.apronId)
                                isApronTileExpanded = true
                            } else {
                                isApronTileExpanded = false
                            }
                        }
                )
            }
        }

        if (isApronTileExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                stands.forEach { stand ->
                    StandTile(stand, userVm, flightVm, standVm)
                }
            }
        }

        if (showCreateStandDialog) {
            Dialog(onDismissRequest = { showCreateStandDialog = false}) {
                CreateStandDialog(
                    onConfirm = { standNumber, A, B, C, D, E, F ->
                        val categories = mutableListOf<AircraftCategory>()
                        if (A) categories.add(AircraftCategory.A)
                        if (B) categories.add(AircraftCategory.B)
                        if (C) categories.add(AircraftCategory.C)
                        if (D) categories.add(AircraftCategory.D)
                        if (E) categories.add(AircraftCategory.E)
                        if (F) categories.add(AircraftCategory.F)
                        standVm.createStand(
                            standNumber = standNumber,
                            categories = categories,
                            apronVm = apronVm
                        ) { ok, msg ->
                            if (ok) {
                                errorMessage = null
                                showCreateStandDialog = false
                            } else {
                                errorMessage = msg
                            }
                        }
                    },
                    onDismiss = {
                        errorMessage = null
                        showCreateStandDialog = false
                    },
                    errorMessage = errorMessage
                )
            }
        }
    }
}