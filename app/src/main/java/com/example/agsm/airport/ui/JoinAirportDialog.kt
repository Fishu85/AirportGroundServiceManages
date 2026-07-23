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
import com.example.agsm.airport.AirportViewModel
import com.example.agsm.ui.theme.Green
import com.example.agsm.ui.theme.PrimaryForeground
import com.example.agsm.ui.theme.SecondaryBackground
import com.example.agsm.ui.theme.SecondaryText
import com.example.agsm.ui.theme.White

@Composable
fun JoinAirportDialog(
    onConfirm: (joinCode: String) -> Unit,
    onDismiss: () -> Unit,
    errorMessage: String? = null,
    airportVm: AirportViewModel
) {
    var joinCode by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SecondaryBackground, RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Join Airport",
            color = White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text("Enter the code to join ${airportVm.airport?.airportName}",
            color = White,
            fontWeight = FontWeight.Bold)

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
            modifier = Modifier.padding(top = 16.dp)
        )

        if(errorMessage != null) {
            Text(errorMessage,
                color = Color.Red)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { onConfirm(joinCode) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green,
                    contentColor = White
                ),
                modifier = Modifier
                    .weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.baseline_done_24),
                        contentDescription = "confirm join the airport",
                        colorFilter = ColorFilter.tint(White)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Confirm",
                        color = White,
                        fontWeight = FontWeight.Bold
                    )
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
                        contentDescription = "cancel join the airport",
                        colorFilter = ColorFilter.tint(White)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Cancel",
                        color = White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}