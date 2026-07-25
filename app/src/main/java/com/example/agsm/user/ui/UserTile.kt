package com.example.agsm.user.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.agsm.R
import com.example.agsm.ui.theme.White
import com.example.agsm.user.Role
import com.example.agsm.user.User
import com.example.agsm.user.UserViewModel

@Composable
fun UserTile(
    user: User,
    userVm: UserViewModel,
    onKickClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(2f)
        ) {
            Text(user.name,
                color = White
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            if (userVm.user?.role == Role.OPERATIONS_MANAGER) {
                Button(
                    onClick = { onKickClick() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = White
                    )
                ) {
                    Text("Kick",
                        color = White,
                        fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}