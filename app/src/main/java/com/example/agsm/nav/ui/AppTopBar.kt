package com.example.agsm.nav.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.agsm.R
import com.example.agsm.ui.theme.SecondaryBackground
import com.example.agsm.ui.theme.White

@Composable
fun AppTopBar(
    nav: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SecondaryBackground)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {

        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.atc_tower),
                    contentDescription = "logo",
                    colorFilter = ColorFilter.tint(White),
                    modifier = Modifier
                        .size(24.dp)
                )

                Text("AGSM",
                    color = White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold)
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Image(
                painter = painterResource(R.drawable.baseline_person_24),
                contentDescription = "profile",
                colorFilter = ColorFilter.tint(White),
                modifier = Modifier
                    .size(32.dp)
                    .clickable{
                        nav.navigate("profile")
                    }
            )
        }
    }
}