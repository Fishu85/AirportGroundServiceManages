package com.example.agsm.airport.ui

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.agsm.R
import com.example.agsm.airport.AirportViewModel
import com.example.agsm.ui.theme.PrimaryBackground
import com.example.agsm.ui.theme.PrimaryForeground
import com.example.agsm.ui.theme.SecondaryBackground
import com.example.agsm.ui.theme.SecondaryForeground
import com.example.agsm.ui.theme.SecondaryText
import com.example.agsm.ui.theme.White
import com.example.agsm.user.Role
import com.example.agsm.user.User
import com.example.agsm.user.UserViewModel
import com.example.agsm.user.ui.KickUserDialog
import com.example.agsm.user.ui.UserTile

@Composable
fun AirportDetailsScreen(
    userVm: UserViewModel,
    airportVm: AirportViewModel
) {
    val airportId = userVm.user?.airportId

    LaunchedEffect(airportId) {
        if (airportId != null) {
            airportVm.getAirport(airportId)
        }
    }

    LaunchedEffect(Unit) {
        userVm.loadAllUsers()
    }

    val users = userVm.users.filter { it.airportId == airportVm.airport?.airportId && it.role == Role.RAMP_SUPERVISOR }

    var isJoinCodeVisible by remember { mutableStateOf(false) }
    var showEditAirportDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf<User?>(null) }
    var showKickDialog by remember { mutableStateOf(false) }
    var userToKick by remember { mutableStateOf<User?>(null) }
    var showLeaveDialog by remember { mutableStateOf(false) }

    if (userVm.user?.airportId != null) {
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
                Text("Airport details",
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    val airport = airportVm.airport

                    Row(
                        modifier = Modifier
                            .padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Name: ",
                            color = White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        Text(airport?.airportName ?: "",
                            color = White,
                            fontSize = 16.sp)
                    }

                    Row(
                        modifier = Modifier
                            .padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("ICAO: ",
                            color = White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        Text(airport?.icao ?: "",
                            color = White,
                            fontSize = 16.sp)
                    }

                    Row(
                        modifier = Modifier
                            .padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("IATA: ",
                            color = White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp)

                        Text(airport?.iata ?: "",
                            color = White,
                            fontSize = 16.sp)
                    }

                    if (userVm.user?.role == Role.OPERATIONS_MANAGER) {
                        Row(
                            modifier = Modifier
                                .padding(top = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Join code: ",
                                color = White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp)

                            if (isJoinCodeVisible) {
                                Text(airport?.joinCode ?: "",
                                    color = White,
                                    fontSize = 16.sp)

                                Spacer(modifier = Modifier.width(8.dp))

                                Image(
                                    painter = painterResource(R.drawable.outline_visibility_24),
                                    contentDescription = "join code visibility: visible",
                                    colorFilter = ColorFilter.tint(White),
                                    modifier = Modifier
                                        .clickable{
                                            isJoinCodeVisible = false
                                        }
                                )
                            } else {
                                Text("********************",
                                    color = White,
                                    fontSize = 16.sp)

                                Spacer(modifier = Modifier.width(8.dp))

                                Image(
                                    painter = painterResource(R.drawable.outline_visibility_off_24),
                                    contentDescription = "Join code visibility: hidden",
                                    colorFilter = ColorFilter.tint(White),
                                    modifier = Modifier
                                        .clickable{
                                            isJoinCodeVisible = true
                                        }
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = { showEditAirportDialog = true },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = SecondaryForeground,
                                    contentColor = White
                                ),
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.baseline_edit_24),
                                        contentDescription = "Edit airport",
                                        colorFilter = ColorFilter.tint(White)
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text("Edit",
                                        color = White,
                                        fontWeight = FontWeight.Bold)
                                }
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Button(
                                onClick = { showDeleteDialog = true },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red,
                                    contentColor = White
                                ),
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.outline_delete_24),
                                        contentDescription = "Delete airport",
                                        colorFilter = ColorFilter.tint(White)
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text("Delete",
                                        color = White,
                                        fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = { showLeaveDialog = true },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red,
                                    contentColor = White
                                )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.baseline_exit_to_app_24),
                                        contentDescription = "Leave the airport",
                                        colorFilter = ColorFilter.tint(White)
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text("Leave",
                                        color = White,
                                        fontWeight = FontWeight.Bold)
                                }
                            }
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
                Text("Employees",
                    color = White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(16.dp))

                Text("Operations manager",
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(airportVm.airport?.manager?.name ?: "",
                        color = White)
                }

                Text("Ramp supervisors",
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp)

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(SecondaryBackground, RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    items(users) { user ->
                        UserTile(user, userVm) {
                            userToKick = user
                            showKickDialog = true
                        }
                    }
                }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimaryBackground),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("You are not part of any airport.",
                color = SecondaryText,
                fontWeight = FontWeight.Bold
            )
        }
    }


    if (showEditAirportDialog) {
        val airport = airportVm.airport
        Dialog(onDismissRequest = { showEditAirportDialog = false } ) {
            EditAirportDialog(
                currentAirportName = airport?.airportName ?: "",
                currentIcao = airport?.icao ?: "",
                currentIata = airport?.iata ?: "",
                currentJoinCode = airport?.joinCode ?: "",
                onConfirm = { airportName, icao, iata, joinCode ->
                    airportVm.updateAirport(
                        airportId = airport!!.airportId,
                        airportName = airportName,
                        icao = icao,
                        iata = iata,
                        joinCode = joinCode
                    ) { ok, msg ->
                        if (ok) {
                            errorMessage = null
                            showEditAirportDialog = false
                        } else {
                            errorMessage = msg
                        }
                    }
                },
                onDismiss = { showEditAirportDialog = false }
            )
        }
    }

    if (showDeleteDialog) {
        val airport = airportVm.airport
        Dialog(onDismissRequest = {showDeleteDialog = false}) {
            DeleteAirportDialog(
                onConfirm = { password ->
                    airportVm.deleteAirport(
                        airportId = airport!!.airportId,
                        password = password,
                        user = userVm.user!!
                    ) { ok, msg ->
                        if (ok) {
                            errorMessage = null
                            showDeleteDialog = false
                            userVm.updateUserAirportId(null) { ok ->
                                if (ok) {
                                    showDeleteDialog = false
                                }
                            }
                            userVm.loadUser()
                        } else {
                            errorMessage = msg
                        }
                    }
                },
                onDismiss = { showDeleteDialog = false },
                errorMessage = errorMessage
            )
        }
    }

    if (showKickDialog) {
        Dialog(onDismissRequest = { showKickDialog = false}) {
            KickUserDialog(
                userName = userToKick!!.name,
                onConfirm = {
                    userVm.updateUserAirportIdFor(userToKick!!.uid, null)
                    userVm.loadAllUsers()
                    showKickDialog = false
                },
                onDismiss = {
                    showKickDialog = false
                }
            )
        }
    }

    if (showLeaveDialog) {
        Dialog(onDismissRequest = {showLeaveDialog = false}) {
            LeaveAirportDialog(
                onConfirm = {
                    userVm.updateUserAirportId(null) { ok ->
                        if (ok) {
                            showLeaveDialog = false
                        }
                    }
                },
                onDismiss = {
                    showLeaveDialog = false
                },
                airportVm = airportVm
            )
        }
    }
}