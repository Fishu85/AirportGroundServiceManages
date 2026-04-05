package com.example.agsm.auth.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.agsm.auth.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agsm.ui.theme.PrimaryBackground
import com.example.agsm.ui.theme.SecondaryBackground
import com.example.agsm.user.Role
import com.example.agsm.user.ui.DropdownMenuRoleSelector
import com.example.agsm.R
import com.example.agsm.ui.theme.PrimaryForeground
import com.example.agsm.ui.theme.PrimaryText
import com.example.agsm.ui.theme.SecondaryForeground
import com.example.agsm.ui.theme.SecondaryText
import com.example.agsm.ui.theme.White

@Composable
fun RegisterScreen(
    vm: AuthViewModel = viewModel(),
    onRegistered: () -> Unit,
    onLoginClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var role by remember { mutableStateOf(Role.RAMP_SUPERVISOR) }

    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }

    if (vm.loggedIn)
        onRegistered()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBackground),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.atc_tower),
                contentDescription = "logo",
                colorFilter = ColorFilter.tint(PrimaryForeground),
                modifier = Modifier
                    .size(64.dp)
            )

            Text("AGSM",
                color = PrimaryForeground,
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .background(SecondaryBackground, RoundedCornerShape(16.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Sign up",
                color = PrimaryForeground,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 16.dp))

            vm.error?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Error: $it",
                    color = Color.Red)
            }

            vm.info?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(it,
                    color = Color.Green)
            }

            TextField(
                value = name,
                onValueChange = {name = it},
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
                    .padding(top = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = email,
                onValueChange = {email = it},
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
                    .padding(top = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = password,
                onValueChange = {password = it},
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
                modifier = Modifier
                    .padding(top = 8.dp),
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
                visualTransformation = if (passwordVisibility) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = confirmPassword,
                onValueChange = {confirmPassword = it},
                label = { Text("Confirm password") },
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
                    .padding(top = 8.dp),
                trailingIcon = {
                    if (confirmPasswordVisibility) {
                        Image(
                            painter = painterResource(R.drawable.outline_visibility_24),
                            contentDescription = "confirm password visibility",
                            colorFilter = ColorFilter.tint(PrimaryText),
                            modifier = Modifier
                                .clickable{
                                    confirmPasswordVisibility = false
                                }
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.outline_visibility_off_24),
                            contentDescription = "confirm password visibility",
                            colorFilter = ColorFilter.tint(SecondaryText),
                            modifier = Modifier
                                .clickable{
                                    confirmPasswordVisibility = true
                                }
                        )
                    }
                },
                visualTransformation = if (confirmPasswordVisibility) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Role:  ",
                    color = SecondaryText,
                    fontSize = 16.sp
                )

                DropdownMenuRoleSelector(role) { role = it }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    vm.register(email, password, confirmPassword, name, role)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = SecondaryForeground,
                    contentColor = White
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp)
            ) {
                Text("Sign up")
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Already have an account? ",
                fontSize = 16.sp,
                color = PrimaryForeground)

            Text("Sign in",
                fontSize = 16.sp,
                color = SecondaryForeground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable{
                        onLoginClick()
                    }
            )
        }
    }
}