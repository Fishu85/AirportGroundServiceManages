package com.example.agsm.user.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agsm.R
import com.example.agsm.auth.AuthViewModel
import com.example.agsm.ui.theme.PrimaryBackground
import com.example.agsm.ui.theme.SecondaryBackground
import com.example.agsm.ui.theme.SecondaryForeground
import com.example.agsm.ui.theme.White
import com.example.agsm.user.User
import com.example.agsm.user.UserViewModel

@Composable
fun ProfileScreen(
    vmAuth: AuthViewModel = viewModel(),
    userVm: UserViewModel,
    onLogout: () -> Unit,
    onReturn: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBackground)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Image(
                    painter = painterResource(R.drawable.outline_keyboard_return_24),
                    contentDescription = "return",
                    colorFilter = ColorFilter.tint(White),
                    modifier = Modifier
                        .clickable{
                            onReturn()
                        }
                        .size(32.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                Image(
                    painter = painterResource(R.drawable.baseline_logout_24),
                    contentDescription = "logout",
                    colorFilter = ColorFilter.tint(White),
                    modifier = Modifier
                        .clickable{
                            vmAuth.logout()
                            onLogout()
                        }
                        .size(32.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(SecondaryBackground, RoundedCornerShape(16.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.baseline_person_24),
                contentDescription = "profile icon",
                colorFilter = ColorFilter.tint(White),
                modifier = Modifier
                    .size(96.dp)
                    .padding(16.dp)
            )

            Column() {
                Text(
                    userVm.user?.name ?: "",
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp)

                Text(
                    userVm.user?.role?.label ?: "",
                    color = White,
                    fontSize = 16.sp)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .background(SecondaryBackground, RoundedCornerShape(16.dp))
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text("Name and surname: ",
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp)

                Text(userVm.user?.name ?: "",
                    color = White,
                    fontSize = 16.sp)
            }

            Row(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text("E-mail: ",
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp)

                Text(userVm.user?.email ?: "",
                    color = White,
                    fontSize = 16.sp)
            }

            Row(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text("Role: ",
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp)

                Text(
                    userVm.user?.role?.label ?: "",
                    color = White,
                    fontSize = 16.sp)
            }

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SecondaryForeground,
                        contentColor = White
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.baseline_edit_24),
                            contentDescription = "edit button",
                            modifier = Modifier
                                .size(16.dp),
                            colorFilter = ColorFilter.tint(White)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text("Edit profile",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp)
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = White
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(R.drawable.outline_delete_24),
                        contentDescription = "delete account",
                        colorFilter = ColorFilter.tint(White),
                        modifier = Modifier
                            .size(16.dp)
                    )

                    Text("Delete account",
                        color = White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp)
                }
            }
        }
    }
}