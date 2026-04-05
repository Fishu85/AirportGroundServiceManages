package com.example.agsm.user.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agsm.R
import com.example.agsm.auth.AuthViewModel
import com.example.agsm.ui.theme.PrimaryBackground
import com.example.agsm.ui.theme.White

@Composable
fun ProfileScreen(
    vmAuth: AuthViewModel = viewModel(),
    onLogout: () -> Unit,
    onReturn: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBackground)
    ) {
        Image(
            painter = painterResource(R.drawable.outline_keyboard_return_24),
            contentDescription = "return",
            colorFilter = ColorFilter.tint(White),
            modifier = Modifier
                .clickable{
                    onReturn()
                }
        )

        Image(
            painter = painterResource(R.drawable.baseline_logout_24),
            contentDescription = "logout",
            colorFilter = ColorFilter.tint(White),
            modifier = Modifier
                .clickable{
                    vmAuth.logout()
                    onLogout()
                }
        )
    }
}