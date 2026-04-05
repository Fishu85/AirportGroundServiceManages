package com.example.agsm.user.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.agsm.ui.theme.PrimaryForeground
import com.example.agsm.ui.theme.PrimaryText
import com.example.agsm.user.Role
import com.example.agsm.R
import com.example.agsm.ui.theme.SecondaryBackground
import com.example.agsm.ui.theme.SecondaryForeground
import com.example.agsm.ui.theme.TertiaryForeground
import com.example.agsm.ui.theme.White

@Composable
fun DropdownMenuRoleSelector(
    selected: Role,
    onSelect: (Role) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var menuWidth by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .onGloballyPositioned { coordinates ->
                menuWidth = coordinates.size.width
            },
    ) {
        Row(
            modifier = Modifier
                .clickable{
                    expanded = true
                }
                .background(PrimaryForeground, RoundedCornerShape(16.dp))
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                selected.label,
                modifier = Modifier
                    .padding(12.dp),
                color = PrimaryText
            )

            if (!expanded) {
                Image(
                    painter = painterResource(R.drawable.baseline_arrow_drop_up_24),
                    contentDescription = "role selection dropdown menu",
                    colorFilter = ColorFilter.tint(White)
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.outline_arrow_drop_down_24),
                    contentDescription = "role selection dropdown menu",
                    colorFilter = ColorFilter.tint(White)
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) {
                    menuWidth.toDp()
                })
                .background(TertiaryForeground)
        ) {
            Role.entries.forEach { role ->
                DropdownMenuItem(
                    text = { Text(role.label,
                        color = PrimaryText) },
                    onClick = {
                        onSelect(role)
                        expanded = false
                    }
                )
            }
        }
    }
}