package com.example.agsm.flight.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.agsm.flight.AircraftCategory
import com.example.agsm.ui.theme.PrimaryForeground
import com.example.agsm.ui.theme.SecondaryBackground
import com.example.agsm.ui.theme.SecondaryForeground
import com.example.agsm.ui.theme.SecondaryText
import com.example.agsm.ui.theme.White

@Composable
fun EditFlightDialog(
    currentModel: String,
    currentCategory: AircraftCategory,
    currentAirline: String,
    currentRegistrationNumber: String,
    currentFlightNumber: String,
    onConfirm: (String, AircraftCategory, String, String, String) -> Unit,
    onDismiss: () -> Unit,
    errorMessage: String? = null
) {
    var model by remember { mutableStateOf(currentModel) }
    var category by remember { mutableStateOf(currentCategory) }
    var airline by remember { mutableStateOf(currentAirline) }
    var registrationNumber by remember { mutableStateOf(currentRegistrationNumber) }
    var flightNumber by remember { mutableStateOf(currentFlightNumber) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SecondaryBackground, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text("Edit flight",
            color = White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp)

        TextField(
            value = model,
            onValueChange = { model = it },
            label = { Text("Aircraft model") },
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

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = airline,
            onValueChange = { airline = it },
            label = { Text("Airline") },
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

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = registrationNumber,
            onValueChange = { registrationNumber = it },
            label = { Text("Registration number") },
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

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = flightNumber,
            onValueChange = { flightNumber = it },
            label = { Text("Flight number") },
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

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Category: ",
                color = SecondaryText,
                fontSize = 16.sp)

            DropdownMenuAircraftCategorySelector(category) {
                category = it
            }
        }

        if(errorMessage != null) {
            Text(errorMessage,
                color = Color.Red,
                modifier = Modifier
                    .padding(top = 16.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { onConfirm(
                    model,
                    category,
                    airline,
                    registrationNumber,
                    flightNumber
                ) },
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
                        contentDescription = "edit flight",
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
    }
}