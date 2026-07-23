package com.example.agsm.airport.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agsm.airport.Airport
import com.example.agsm.ui.theme.SecondaryBackground
import com.example.agsm.ui.theme.SecondaryForeground
import com.example.agsm.ui.theme.White
import com.example.agsm.user.Role
import com.example.agsm.user.UserViewModel

@Composable
fun AirportTile(
    airport: Airport,
    userVm: UserViewModel,
    onJoinClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(SecondaryBackground),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(2f)
                .padding(8.dp)
        ) {
            Text(airport.airportName,
                color = White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Text("${airport.icao}/${airport.iata}",
                color = White,
                fontSize = 16.sp)
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            horizontalAlignment = Alignment.End
        ) {
            if (userVm.user?.airportId == null && userVm.user?.role == Role.RAMP_SUPERVISOR){
                Button(
                    onClick = { onJoinClick() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SecondaryForeground,
                        contentColor = White
                    )
                ) {
                    Text("Join",
                        color = White,
                        fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}