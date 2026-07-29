package com.example.agsm.airport.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.agsm.ui.theme.White

@Composable
fun LeaveAirportDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    airportVm: AirportViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryForeground, RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Are you sure you want to leave ${airportVm.airport?.airportName}?",
            color = White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                onClick = { onConfirm() },
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
                        contentDescription = "Confirm leave airport",
                        colorFilter = ColorFilter.tint(White)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Confirm",
                        color = White,
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
                        contentDescription = "Cancel leaving the airport",
                        colorFilter = ColorFilter.tint(White)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Cancel",
                        color = White,
                        fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}