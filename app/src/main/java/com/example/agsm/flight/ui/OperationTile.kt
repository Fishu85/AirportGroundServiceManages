package com.example.agsm.flight.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.agsm.R
import com.example.agsm.ui.theme.SecondaryForeground
import com.example.agsm.ui.theme.White

@Composable
fun OperationTile(
    operation: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(2f)
                .padding(8.dp)
                .background(SecondaryForeground, RoundedCornerShape(16.dp))
                .padding(8.dp)
        ) {
            Text(operation,
                color = White,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Image(
                painter = painterResource(R.drawable.outline_delete_24),
                contentDescription = "delete operation",
                colorFilter = ColorFilter.tint(Color.Red)
            )
        }
    }
}