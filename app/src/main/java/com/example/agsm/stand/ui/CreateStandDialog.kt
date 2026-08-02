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
import com.example.agsm.ui.theme.Green
import com.example.agsm.ui.theme.PrimaryForeground
import com.example.agsm.ui.theme.SecondaryBackground
import com.example.agsm.ui.theme.SecondaryText
import com.example.agsm.ui.theme.White

@Composable
fun CreateStandDialog(
    onConfirm: (standNumber: String, A: Boolean, B: Boolean, C: Boolean, D:Boolean, E: Boolean, F: Boolean) -> Unit,
    onDismiss: () -> Unit,
    errorMessage: String? = null
) {
    var standNumber by remember { mutableStateOf("") }

    var isASelected by remember { mutableStateOf(false) }
    var isBSelected by remember { mutableStateOf(false) }
    var isCSelected by remember { mutableStateOf(false) }
    var isDSelected by remember { mutableStateOf(false) }
    var isESelected by remember { mutableStateOf(false) }
    var isFSelected by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SecondaryBackground, RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Create Stand",
            color = White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Enter the name of the stand",
            color = White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp)

        TextField(
            value = standNumber,
            onValueChange = { standNumber = it },
            label = { Text("Name") },
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
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Categories: ",
                color = White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.width(16.dp))

            if (isASelected) {
                Text("A",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Green,
                    modifier = Modifier
                        .clickable {
                            isASelected = false
                        }
                )
            } else {
                Text("A",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    modifier = Modifier
                        .clickable {
                            isASelected = true
                        }
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            if (isBSelected) {
                Text("B",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Green,
                    modifier = Modifier
                        .clickable {
                            isBSelected = false
                        }
                )
            } else {
                Text("B",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    modifier = Modifier
                        .clickable {
                            isBSelected = true
                        }
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            if (isCSelected) {
                Text("C",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Green,
                    modifier = Modifier
                        .clickable {
                            isCSelected = false
                        }
                )
            } else {
                Text("C",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    modifier = Modifier
                        .clickable {
                            isCSelected = true
                        }
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            if (isDSelected) {
                Text("D",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Green,
                    modifier = Modifier
                        .clickable {
                            isDSelected = false
                        }
                )
            } else {
                Text("D",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    modifier = Modifier
                        .clickable {
                            isDSelected = true
                        }
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            if (isESelected) {
                Text("E",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Green,
                    modifier = Modifier
                        .clickable {
                            isESelected = false
                        }
                )
            } else {
                Text("E",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    modifier = Modifier
                        .clickable {
                            isESelected = true
                        }
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            if (isFSelected) {
                Text("F",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Green,
                    modifier = Modifier
                        .clickable {
                            isFSelected = false
                        }
                )
            } else {
                Text("F",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    modifier = Modifier
                        .clickable {
                            isFSelected = true
                        }
                )
            }

        }

        if(errorMessage != null) {
            Text(errorMessage,
                color = Color.Red,
                modifier = Modifier
                    .padding(top = 8.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { onConfirm(
                    standNumber,
                    isASelected,
                    isBSelected,
                    isCSelected,
                    isDSelected,
                    isESelected,
                    isFSelected
                ) },
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
                        contentDescription = "create stand",
                        colorFilter = ColorFilter.tint(White)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Create",
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