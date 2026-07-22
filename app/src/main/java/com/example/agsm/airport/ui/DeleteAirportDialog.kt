package com.example.agsm.airport.ui

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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agsm.R
import com.example.agsm.ui.theme.PrimaryForeground
import com.example.agsm.ui.theme.SecondaryBackground
import com.example.agsm.ui.theme.SecondaryForeground
import com.example.agsm.ui.theme.SecondaryText
import com.example.agsm.ui.theme.White

@Composable
fun DeleteAirportDialog(
    onConfirm: (password: String) -> Unit,
    onDismiss: () -> Unit,
    errorMessage: String? = null
) {
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(SecondaryBackground, RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Delete airport",
            color = White,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Enter your password to delete the airport.",
            color = White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold)

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            trailingIcon = {
                if (passwordVisibility) {
                    Image(
                        painter = painterResource(R.drawable.outline_visibility_24),
                        contentDescription = "password visibility visible",
                        colorFilter = ColorFilter.tint(SecondaryText),
                        modifier = Modifier
                            .clickable {
                                passwordVisibility = false
                            }
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.outline_visibility_off_24),
                        contentDescription = "password visibility hidden",
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
            ),
            modifier = Modifier
                .padding(top = 16.dp)
        )

        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
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
                onClick = { onConfirm(password) },
                modifier = Modifier
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = White
                )
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

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { onDismiss() },
                modifier = Modifier
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SecondaryForeground,
                    contentColor = White
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.baseline_close_24),
                        contentDescription = "Cancel airport deletion",
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