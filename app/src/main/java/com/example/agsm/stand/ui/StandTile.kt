package com.example.agsm.stand.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.agsm.R
import com.example.agsm.apron.ApronViewModel
import com.example.agsm.flight.AircraftCategory
import com.example.agsm.flight.AircraftPosition
import com.example.agsm.flight.FlightViewModel
import com.example.agsm.flight.ui.AddServiceDialog
import com.example.agsm.flight.ui.CreateFlightDialog
import com.example.agsm.flight.ui.DeleteFlightDialog
import com.example.agsm.flight.ui.DropdownMenuAircraftPositionSelector
import com.example.agsm.flight.ui.EditFlightDialog
import com.example.agsm.flight.ui.OperationTile
import com.example.agsm.stand.Stand
import com.example.agsm.stand.StandViewModel
import com.example.agsm.ui.theme.Green
import com.example.agsm.ui.theme.PrimaryBackground
import com.example.agsm.ui.theme.SecondaryForeground
import com.example.agsm.ui.theme.White
import com.example.agsm.user.Role
import com.example.agsm.user.UserViewModel

@Composable
fun StandTile(
    stand: Stand?,
    userVm: UserViewModel,
    flightVm: FlightViewModel,
    standVm: StandViewModel,
    apronVm: ApronViewModel
) {
    var isStandTileExpanded by remember { mutableStateOf(false) }

    val categories = stand?.categories ?: emptyList()

    /*
    var isAEnabled by remember { mutableStateOf(categories.contains(AircraftCategory.A)) }
    var isBEnabled by remember { mutableStateOf(categories.contains(AircraftCategory.B)) }
    var isCEnabled by remember { mutableStateOf(categories.contains(AircraftCategory.C)) }
    var isDEnabled by remember { mutableStateOf(categories.contains(AircraftCategory.D)) }
    var isEEnabled by remember { mutableStateOf(categories.contains(AircraftCategory.E)) }
    var isFEnabled by remember { mutableStateOf(categories.contains(AircraftCategory.F)) }


     */
    val isAEnabled = categories.contains(AircraftCategory.A)
    val isBEnabled = categories.contains(AircraftCategory.B)
    val isCEnabled = categories.contains(AircraftCategory.C)
    val isDEnabled = categories.contains(AircraftCategory.D)
    val isEEnabled = categories.contains(AircraftCategory.E)
    val isFEnabled = categories.contains(AircraftCategory.F)

    var showAddFlightDialog by remember { mutableStateOf(false) }
    var showAddServiceDialog by remember { mutableStateOf(false) }
    var showDeleteFlightDialog by remember { mutableStateOf(false) }
    var showDeleteStandDialog by remember { mutableStateOf(false) }
    var showEditFlightDialog by remember { mutableStateOf(false) }
    var showEditStandDialog by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    val aircraftPosition = stand?.flight?.aircraftPosition ?: AircraftPosition.ARRIVAL

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .background(PrimaryBackground, RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(8f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    stand?.standNumber ?: "",
                    color = White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                if (userVm.user?.role == Role.OPERATIONS_MANAGER) {
                    Image(
                        painter = painterResource(R.drawable.baseline_edit_24),
                        contentDescription = "edit stand",
                        colorFilter = ColorFilter.tint(White),
                        modifier = Modifier
                            .clickable{
                                showEditStandDialog = true
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
                if (userVm.user?.role == Role.OPERATIONS_MANAGER) {
                    Image(
                        painter = painterResource(R.drawable.outline_delete_24),
                        contentDescription = "delete stand",
                        colorFilter = ColorFilter.tint(Color.Red),
                        modifier = Modifier
                            .clickable{
                                showDeleteStandDialog = true
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(2f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if (isAEnabled) {
                        Text("A",
                            color = Green,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text("A",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    if (isBEnabled) {
                        Text("B",
                            color = Green,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text("B",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    if (isCEnabled) {
                        Text("C",
                            color = Green,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text("C",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if (isDEnabled) {
                        Text("D",
                            color = Green,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text("D",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    if (isEEnabled) {
                        Text("E",
                            color = Green,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text("E",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    if (isFEnabled) {
                        Text("F",
                            color = Green,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text("F",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }
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
                        if (!isStandTileExpanded) {
                            R.drawable.baseline_arrow_drop_up_24
                        } else {
                            R.drawable.outline_arrow_drop_down_24
                        }
                    ),
                    contentDescription = "expand stand tile",
                    colorFilter = ColorFilter.tint(White),
                    modifier = Modifier
                        .clickable {
                            if (!isStandTileExpanded) {
                                isStandTileExpanded = true
                            } else {
                                isStandTileExpanded = false
                            }
                        }
                )
            }
        }

        if (isStandTileExpanded) {
            flightVm.loadFlightForStand(standVm, stand?.standId ?: "")
            Spacer(modifier = Modifier.height(16.dp))

            if (stand?.flight == null && userVm.user?.role == Role.OPERATIONS_MANAGER) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { showAddFlightDialog = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SecondaryForeground,
                            contentColor = White
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("+",
                                color = White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold)

                            Spacer(modifier = Modifier.width(8.dp))

                            Text("Add flight",
                                color = White,
                                fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            if(stand?.flight != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(5f)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(stand.flight.aircraftModel,
                                    color = White,
                                    fontWeight = FontWeight.Bold)
                            }

                            Column(
                                modifier = Modifier
                                    .weight(1f),
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(stand.flight.airline,
                                    color = White,
                                    fontWeight = FontWeight.Bold)
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(stand.flight.registrationNumber,
                                    color = White,
                                    fontWeight = FontWeight.Bold)
                            }

                            Column(
                                modifier = Modifier
                                    .weight(1f),
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(stand.flight.flightNumber,
                                    color = White,
                                    fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    if (userVm.user?.role == Role.OPERATIONS_MANAGER) {
                        Column(
                            modifier = Modifier
                                .weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(R.drawable.baseline_edit_24),
                                contentDescription = "edit flight",
                                colorFilter = ColorFilter.tint(White),
                                modifier = Modifier
                                    .clickable { showEditFlightDialog = true }
                            )
                        }
                    }
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    DropdownMenuAircraftPositionSelector(aircraftPosition) { newPosition ->
                        flightVm.updateFlightPositionForStand(standVm, stand, newPosition)
                    }
                }

                if (aircraftPosition == AircraftPosition.ON_BLOCKS) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        stand.flight.operations.forEach { service ->
                            OperationTile(service) {
                                flightVm.deleteService(standVm, service) { ok, msg ->
                                    if (!ok) {
                                        errorMessage = msg
                                    } else {
                                        errorMessage = null
                                    }
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("+",
                            color = White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp,
                            modifier = Modifier
                                .clickable { showAddServiceDialog = true }
                        )
                    }
                }

                if (aircraftPosition == AircraftPosition.DEPARTURE && userVm.user?.role == Role.OPERATIONS_MANAGER) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { showDeleteFlightDialog = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red,
                                contentColor = White
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.outline_delete_24),
                                    contentDescription = "delete flight",
                                    colorFilter = ColorFilter.tint(White)
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text("Delete",
                                    color = White,
                                    fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddFlightDialog) {
        Dialog(onDismissRequest = {showAddFlightDialog = false}) {
            CreateFlightDialog(
                onConfirm = { aircraftModel, aircraftCategory, airline, registrationNumber, flightNumber ->
                    standVm.updateStand(stand)
                    flightVm.createFlight(
                        standVm = standVm,
                        aircraftModel = aircraftModel,
                        aircraftCategory = aircraftCategory,
                        airline = airline,
                        registrationNumber = registrationNumber,
                        flightNumber = flightNumber
                    ) { ok, msg ->
                        if (ok) {
                            errorMessage = null
                            showAddFlightDialog = false
                        } else {
                            errorMessage = msg
                        }
                    }
                },
                onDismiss = {
                    errorMessage = null
                    showAddFlightDialog = false
                },
                errorMessage = errorMessage
            )
        }
    }

    if (showEditFlightDialog) {
        val flight = flightVm.flight
        Dialog(onDismissRequest = { showEditFlightDialog = false }) {
            EditFlightDialog(
                currentModel = flight?.aircraftModel ?: "",
                currentCategory = flight?.aircraftCategory ?: AircraftCategory.A,
                currentAirline = flight?.airline ?: "",
                currentRegistrationNumber = flight?.registrationNumber ?: "",
                currentFlightNumber = flight?.flightNumber ?: "",
                onConfirm = { aircraftModel, aircraftCategory, airline, registrationNumber, flightNumber ->
                    standVm.updateStand(stand)
                    flightVm.updateFlight(
                        standVm = standVm,
                        aircraftModel = aircraftModel,
                        aircraftCategory = aircraftCategory,
                        airline = airline,
                        registrationNumber = registrationNumber,
                        flightNumber = flightNumber
                    ) { ok, msg ->
                        if (ok) {
                            errorMessage = null
                            showEditFlightDialog = false
                        } else {
                            errorMessage = msg
                        }
                    }
                },
                onDismiss = {
                    errorMessage = null
                    showEditFlightDialog = false
                },
                errorMessage = errorMessage
            )
        }
    }

    if (showEditStandDialog) {
        val stand = standVm.stand
        Dialog(onDismissRequest = { showEditStandDialog = false}) {
            EditStandDialog(
                currentStandNumber = stand?.standNumber ?: "",
                currentCategories = stand?.categories ?: emptyList(),
                onConfirm = { standNumber, categories ->
                    standVm.editStand(
                        apronVm = apronVm,
                        standNumber = standNumber,
                        categories = categories
                    ) { ok, msg ->
                        if (ok) {
                            showEditStandDialog = false
                            errorMessage = null
                        } else {
                            errorMessage = msg
                        }
                    }
                },
                onDismiss = {
                    showEditStandDialog = false
                    errorMessage = null
                },
                errorMessage = errorMessage
            )
        }
    }

    if (showAddServiceDialog) {
        Dialog(onDismissRequest = { showAddServiceDialog = false }) {
            AddServiceDialog(
                onConfirm = { service ->
                    flightVm.loadFlightForStand(standVm, stand?.standId ?: "")
                    flightVm.addServiceToFlight(standVm, service) { ok, msg ->
                        if (ok) {
                            errorMessage = null
                            showAddServiceDialog = false
                        } else {
                            errorMessage = msg
                        }
                    }
                },
                onDismiss = {
                    errorMessage = null
                    showAddServiceDialog = false
                },
                errorMessage = errorMessage
            )
        }
    }

    if (showDeleteFlightDialog) {
        Dialog(onDismissRequest = { showDeleteFlightDialog = false }) {
            DeleteFlightDialog(
                onConfirm = {
                    flightVm.deleteFlight(standVm, stand!!) { ok, msg ->
                        if (ok) {
                            errorMessage = null
                            showDeleteFlightDialog = false
                        } else {
                            errorMessage = msg
                        }
                    }
                },
                onDismiss = {
                    errorMessage = null
                    showDeleteFlightDialog = false
                },
                errorMessage = errorMessage
            )
        }
    }

    if (showDeleteStandDialog) {
        Dialog(onDismissRequest = { showDeleteStandDialog = false }) {
            DeleteStandDialog(
                onConfirm = {
                    standVm.deleteStand(stand!!) { ok, msg ->
                        if (ok) {
                            errorMessage = null
                            showDeleteStandDialog = false
                        } else {
                            errorMessage = msg
                        }
                    }
                },
                onDismiss = {
                    errorMessage = null
                    showDeleteStandDialog = false
                },
                errorMessage = errorMessage
            )
        }
    }
}