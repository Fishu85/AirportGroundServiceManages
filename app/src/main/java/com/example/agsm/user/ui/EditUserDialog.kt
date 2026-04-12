package com.example.agsm.user.ui

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
import androidx.compose.ui.window.Dialog
import com.example.agsm.R
import com.example.agsm.ui.theme.Green
import com.example.agsm.ui.theme.PrimaryForeground
import com.example.agsm.ui.theme.PrimaryText
import com.example.agsm.ui.theme.SecondaryBackground
import com.example.agsm.ui.theme.SecondaryText
import com.example.agsm.ui.theme.White

@Composable
fun EditUserDialog(
    currentName: String,
    currentEmail: String,
    errorMessage: String?,
    onConfirm: (String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf(currentName) }
    var email by remember { mutableStateOf(currentEmail) }
    var localError by remember { mutableStateOf<String?>(null) }

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .background(SecondaryBackground, RoundedCornerShape(16.dp))
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Edit profile",
                color = PrimaryText,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (localError != null) {
                Text(localError!!,
                    color = Color.Red)
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (errorMessage != null) {
                Text(errorMessage,
                    color = Color.Red)
                Spacer(modifier = Modifier.height(8.dp))
            }

            TextField(
                value = name,
                onValueChange = {
                    name = it
                    localError = null
                },
                label = { Text("Name and surname") },
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

            TextField(
                value = email,
                onValueChange = {
                    email = it
                    localError = null
                },
                label = { Text("E-mail") },
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
            ) {
                Button(
                    onClick = {
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.baseline_close_24),
                            contentDescription = "cancel editing profile",
                            colorFilter = ColorFilter.tint(Color.White)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text("Cancel",
                            fontWeight = FontWeight.Bold,
                            color = Color.White)
                    }
                }

                Button(
                    onClick = {
                        onConfirm(name, email)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Green,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.baseline_done_24),
                            contentDescription = "confirm edit",
                            colorFilter = ColorFilter.tint(Color.White)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text("Confirm",
                            fontWeight = FontWeight.Bold,
                            color = Color.White)
                    }
                }
            }
        }
    }
1}