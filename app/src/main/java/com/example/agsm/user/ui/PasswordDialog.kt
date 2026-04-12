package com.example.agsm.user.ui

import androidx.compose.foundation.Image
import androidx.compose.ui.window.Dialog
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agsm.R
import com.example.agsm.ui.theme.Green
import com.example.agsm.ui.theme.PrimaryForeground
import com.example.agsm.ui.theme.PrimaryText
import com.example.agsm.ui.theme.SecondaryBackground
import com.example.agsm.ui.theme.SecondaryText
import com.example.agsm.ui.theme.White

@Composable
fun PasswordDialog(
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit,
    errorMessage: String?
) {
    var password by remember { mutableStateOf("") }
    var localError by remember { mutableStateOf<String?>(null) }
    var passwordVisibility by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .background(SecondaryBackground, RoundedCornerShape(16.dp))
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Text("Confirm password",
                color = PrimaryForeground,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp)

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
                value = password,
                onValueChange = {
                    password = it
                    localError = null
                },
                label = { Text("Password") },
                trailingIcon = {
                    if (passwordVisibility) {
                        Image(
                            painter = painterResource(R.drawable.outline_visibility_24),
                            contentDescription = "password visibility",
                            colorFilter = ColorFilter.tint(SecondaryText),
                            modifier = Modifier
                                .clickable{
                                    passwordVisibility = false
                                }
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.outline_visibility_off_24),
                            contentDescription = "password visibility",
                            colorFilter = ColorFilter.tint(SecondaryText),
                            modifier = Modifier
                                .clickable{
                                    passwordVisibility = true
                                }
                        )
                    }
                },
                visualTransformation =
                    if (passwordVisibility) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
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
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
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
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.baseline_close_24),
                            contentDescription = "cancel",
                            colorFilter = ColorFilter.tint(Color.White)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text("Cancel",
                            color = Color.White,
                            fontWeight = FontWeight.Bold)
                    }
                }

                Button(
                    onClick = {
                        if (password.isBlank()) {
                            localError = "Password cannot be emptu"
                            return@Button
                        }
                        onConfirm(password)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Green,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.baseline_done_24),
                            contentDescription = "confirm",
                            colorFilter = ColorFilter.tint(Color.White)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text("Confirm",
                            color = Color.White,
                            fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}