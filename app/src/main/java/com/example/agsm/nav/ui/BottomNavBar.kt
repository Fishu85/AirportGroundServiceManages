package com.example.agsm.nav.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.agsm.R
import com.example.agsm.ui.theme.SecondaryBackground
import com.example.agsm.ui.theme.White

@Composable
fun BottomNavBar(
    nav: NavController
) {
    NavigationBar(
        containerColor = SecondaryBackground
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { nav.navigate("airport_list") },
            icon = { Image(
                painter = painterResource(R.drawable.baseline_format_list_bulleted_24),
                contentDescription = "Airport list",
                colorFilter = ColorFilter.tint(White)
            ) },
            label = { Text("Airport list") },
            colors = NavigationBarItemDefaults.colors(
                unselectedTextColor = White,
                selectedTextColor = White
            )
        )

        NavigationBarItem(
            selected = false,
            onClick = { nav.navigate("airport_details")},
            icon = {Image(
                painter = painterResource(R.drawable.baseline_home_24),
                contentDescription = "Home airport",
                colorFilter = ColorFilter.tint(White)
            )},
            label = { Text("Home airport")},
            colors = NavigationBarItemDefaults.colors(
                unselectedTextColor = White,
                selectedTextColor = White
            )
        )

        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Image(
                painter = painterResource(R.drawable.baseline_connecting_airports_24),
                contentDescription = "Operations",
                colorFilter = ColorFilter.tint(White)
            )},
            label = { Text("Operations")},
            colors = NavigationBarItemDefaults.colors(
                unselectedTextColor = White,
                selectedTextColor = White
            )
        )
    }
}