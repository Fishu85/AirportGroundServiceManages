package com.example.agsm.user.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.agsm.R
import com.example.agsm.ui.theme.PrimaryForeground
import com.example.agsm.ui.theme.SecondaryBackground
import com.example.agsm.ui.theme.SecondaryText
import com.example.agsm.ui.theme.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.input.VisualTransformation
import com.example.agsm.ui.theme.SecondaryForeground

@Composable
fun DeleteAccountDialog(
    errorMessage: String?,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Delete account",
                fontWeight = FontWeight.Bold,
                color = White
            )
        },
        text = {
            Column{
                Text("To delete your account, please enter your password.",
                    color = White)

                Spacer(modifier = Modifier.height(12.dp))

                if (error != null) {
                    Text(error!!,
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
                    onValueChange = { password = it
                                    error = null},
                    label = { Text("Password") },
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
                    trailingIcon = {
                        if (passwordVisibility) {
                            Image(
                                painter = painterResource(R.drawable.outline_visibility_24),
                                contentDescription = "password visibility",
                                colorFilter = ColorFilter.tint(SecondaryText),
                                modifier = Modifier
                                    .clickable {
                                        passwordVisibility = false
                                    }
                            )
                        } else {
                            Image(
                                painter = painterResource(R.drawable.outline_visibility_off_24),
                                contentDescription = "password visibility",
                                colorFilter = ColorFilter.tint(SecondaryText),
                                modifier = Modifier
                                    .clickable {
                                        passwordVisibility = true
                                    }
                            )
                        }
                    },
                    visualTransformation = if (passwordVisibility) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (password.isBlank()) {
                        error = "Password cannot be empty"
                    } else {
                        onConfirm(password)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.outline_delete_24),
                        contentDescription = "confirm delete account",
                        colorFilter = ColorFilter.tint(White)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Delete",
                        color = White,
                        fontWeight = FontWeight.Bold)
                }

            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = SecondaryForeground
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.baseline_close_24),
                        contentDescription = "cancel account deletion",
                        colorFilter = ColorFilter.tint(White)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Cancel",
                        color = White,
                        fontWeight = FontWeight.Bold)
                }
            }
        },
        containerColor = SecondaryBackground
    )
}