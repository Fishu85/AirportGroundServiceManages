package com.example.agsm.stand.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agsm.R
import com.example.agsm.flight.AircraftCategory
import com.example.agsm.stand.Stand
import com.example.agsm.ui.theme.Green
import com.example.agsm.ui.theme.PrimaryBackground
import com.example.agsm.ui.theme.White
import com.example.agsm.user.UserViewModel

@Composable
fun StandTile(
    stand: Stand?,
    userVm: UserViewModel
) {
    var isStandTileExpanded by remember { mutableStateOf(false) }

    val categories = stand?.categories ?: emptyList()

    var isAEnabled by remember { mutableStateOf(categories.contains(AircraftCategory.A)) }
    var isBEnabled by remember { mutableStateOf(categories.contains(AircraftCategory.B)) }
    var isCEnabled by remember { mutableStateOf(categories.contains(AircraftCategory.C)) }
    var isDEnabled by remember { mutableStateOf(categories.contains(AircraftCategory.D)) }
    var isEEnabled by remember { mutableStateOf(categories.contains(AircraftCategory.E)) }
    var isFEnabled by remember { mutableStateOf(categories.contains(AircraftCategory.F)) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .background(PrimaryBackground, RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(10f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    stand?.standNumber ?: "",
                    color = White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(2f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if (isAEnabled) {
                        Text("A",
                            color = Green,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text("A",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    if (isBEnabled) {
                        Text("B",
                            color = Green,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text("B",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    if (isCEnabled) {
                        Text("C",
                            color = Green,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text("C",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if (isDEnabled) {
                        Text("D",
                            color = Green,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text("D",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    if (isEEnabled) {
                        Text("E",
                            color = Green,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text("E",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    if (isFEnabled) {
                        Text("F",
                            color = Green,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text("F",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                Image(
                    painter = painterResource(
                        if (!isStandTileExpanded) {
                            R.drawable.baseline_arrow_drop_up_24
                        } else {
                            R.drawable.outline_arrow_drop_down_24
                        }
                    ),
                    contentDescription = "expand stand tile",
                    colorFilter = ColorFilter.tint(White),
                    modifier = Modifier
                        .clickable {
                            if (!isStandTileExpanded) {
                                isStandTileExpanded = true
                            } else {
                                isStandTileExpanded = false
                            }
                        }
                )
            }
        }

        if (isStandTileExpanded) {

        }
    }
}