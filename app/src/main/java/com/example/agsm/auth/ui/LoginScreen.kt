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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agsm.R
import com.example.agsm.auth.AuthViewModel
import com.example.agsm.auth.AuthViewModelFactory
import com.example.agsm.auth.EmailHistory
import com.example.agsm.ui.theme.PrimaryBackground
import com.example.agsm.ui.theme.PrimaryForeground
import com.example.agsm.ui.theme.SecondaryBackground
import com.example.agsm.ui.theme.SecondaryForeground
import com.example.agsm.ui.theme.SecondaryText
import com.example.agsm.ui.theme.TertiaryForeground
import com.example.agsm.ui.theme.White
import com.example.agsm.user.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    userVm: UserViewModel = viewModel(),
    onLoggedIn: () -> Unit,
    onRegisterClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var passwordVisibility by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val vm: AuthViewModel = viewModel(factory = AuthViewModelFactory(context))

    val savedEmails by EmailHistory.getEmails(context).collectAsState(initial = emptyList())
    var expanded by remember { mutableStateOf(false) }

    if (vm.loggedIn) {
        userVm.loadUser()
        onLoggedIn()
    }

    Column(
        modifier = Modifier
            .background(PrimaryBackground)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
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
                .padding(24.dp)
                .fillMaxWidth()
                .background(SecondaryBackground, RoundedCornerShape(16.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Sign in",
                color = PrimaryForeground,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 16.dp))

            vm.error?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Error: $it",
                    color = Color.Red,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
            }

            vm.info?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(it,
                    color = Color.Green)
            }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                TextField(
                    value = email,
                    onValueChange = { email = it },
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
                        .menuAnchor()
                        .clickable{
                            expanded = true
                        }
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    containerColor = TertiaryForeground
                ) {
                    savedEmails
                        .filter { it.startsWith(email, ignoreCase = true) }
                        .forEach { suggestion ->
                            DropdownMenuItem(
                                text = { Text(suggestion,
                                    color = SecondaryText) },
                                onClick = {
                                    email = suggestion
                                    expanded = false
                                }
                            )
                        }
                }
            }



            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
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
                    .padding(top = 16.dp),
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp, top = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text("Forgot password?",
                    color = SecondaryForeground,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable {
                            vm.resetPassword(email)
                        }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    vm.login(email, password)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = SecondaryForeground,
                    contentColor = White
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp)
            ) {
                Text("Sign in")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Don't have an account? ",
                color = PrimaryForeground,
                fontSize = 16.sp)

            Text("Sign up",
                color = SecondaryForeground,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable{
                        onRegisterClick()
                    }
            )
        }
    }
}