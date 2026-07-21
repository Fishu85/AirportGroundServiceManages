package com.example.agsm.airport.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import com.example.agsm.R
import com.example.agsm.ui.theme.Green
import com.example.agsm.ui.theme.PrimaryForeground
import com.example.agsm.ui.theme.SecondaryBackground
import com.example.agsm.ui.theme.SecondaryForeground
import com.example.agsm.ui.theme.SecondaryText
import com.example.agsm.ui.theme.White

@Composable
fun EditAirportDialog(
    currentAirportName: String,
    currentIcao: String,
    currentIata: String,
    currentJoinCode: String,
    onConfirm: (airportName: String, icao: String, iata: String, joinCode: String) -> Unit,
    onDismiss: () -> Unit,
    errorMessage: String? = null
) {
    var airportName by remember { mutableStateOf(currentAirportName) }
    var icao by remember { mutableStateOf(currentIcao) }
    var iata by remember { mutableStateOf(currentIata) }
    var joinCode by remember { mutableStateOf(currentJoinCode) }

    Column(
        modifier = Modifier
            .background(SecondaryBackground, RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Edit airport",
            color = White,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        TextField(
            value = airportName,
            onValueChange = { airportName = it },
            label = { Text("Airport name") },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = PrimaryForeground,
                focusedContainerColor = PrimaryForeground,
                unfocusedTextColor = SecondaryText,
                focusedTextColor = White,
                unfocusedLabelColor = SecondaryText,
                focusedLabelColor = White,
                unfocusedIndicatorColor = PrimaryForeground,
                focusedIndicatorColor = White,
                cursorColor = White
            ),
            modifier = Modifier
                .padding(top = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                value = icao,
                onValueChange = { icao = it },
                label = { Text("ICAO code") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = PrimaryForeground,
                    focusedContainerColor = PrimaryForeground,
                    unfocusedTextColor = SecondaryText,
                    focusedTextColor = White,
                    unfocusedLabelColor = SecondaryText,
                    focusedLabelColor = White,
                    unfocusedIndicatorColor = PrimaryForeground,
                    focusedIndicatorColor = White,
                    cursorColor = White
                ),
                modifier = Modifier
                    .weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            TextField(
                value = iata,
                onValueChange = { iata = it },
                label = { Text("IATA code") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = PrimaryForeground,
                    focusedContainerColor = PrimaryForeground,
                    unfocusedTextColor = SecondaryText,
                    focusedTextColor = White,
                    unfocusedLabelColor = SecondaryText,
                    focusedLabelColor = White,
                    unfocusedIndicatorColor = PrimaryForeground,
                    focusedIndicatorColor = White,
                    cursorColor = White
                ),
                modifier = Modifier
                    .weight(1f)
            )
        }

        TextField(
            value = joinCode,
            onValueChange = { joinCode = it },
            label = { Text("Join code") },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = PrimaryForeground,
                focusedContainerColor = PrimaryForeground,
                unfocusedTextColor = SecondaryText,
                focusedTextColor = White,
                unfocusedLabelColor = SecondaryText,
                focusedLabelColor = White,
                unfocusedIndicatorColor = PrimaryForeground,
                focusedIndicatorColor = White,
                cursorColor = White
            ),
            modifier = Modifier
                .padding(top = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { onConfirm(airportName, icao, iata, joinCode) },
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
                        contentDescription = "edit airport",
                        colorFilter = ColorFilter.tint(White)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Edit",
                        fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { onDismiss() },
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
                        painter = painterResource(R.drawable.baseline_close_24),
                        contentDescription = "Cancel",
                        colorFilter = ColorFilter.tint(White)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Cancel",
                        fontWeight = FontWeight.Bold)
                }
            }
        }

        if(errorMessage != null) {
            Text(errorMessage,
                color = Color.Red,
                modifier = Modifier
                    .padding(top = 8.dp))
        }
    }
}