package com.nprg056.gribki

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow


@Composable
fun MushroomScreen(
    state: MushroomState,
    onEvent: (MushroomEvent) -> Unit,
    onMushroomClick: (Int) -> Unit,
    screenSettingClick: () -> Unit
){
    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF90EE90))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // heading
                        Text(
                            text = "Atlas Hub",
                            color = Color.Black,
                            fontSize = (state.fontSize * 2).sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f).padding(vertical = 15.dp)
                        )

                        // settings button
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(Color.White, RoundedCornerShape(24.dp))
                                .clickable { screenSettingClick() }
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "⚙️",
                                fontSize = (state.fontSize).sp
                            )
                        }
                    }
                }
            }

            // search bar
            item {
                OutlinedTextField(
                    value = state.searchedName,
                    onValueChange = { newValue ->
                        onEvent(MushroomEvent.SearchMushroomName(newValue))
                    },
                    label = { Text("Název houby:", fontSize = (state.fontSize).sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )

                // mushroom edibility radio button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val czechLabels = mapOf(
                        "Vsechny" to "Všechny",
                        "Jedla" to "Jedlé",
                        "Nejedla" to "Nejedlé",
                        "Jedovata" to "Jedovaté"
                    )
                    UsageType.entries.forEach { usageType ->
                        Row(
                            modifier = Modifier
                                .clickable {
                                    onEvent(MushroomEvent.SortMushroom(usageType))
                                }
                                 .padding(horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = state.usage == usageType,
                                onClick = {
                                    onEvent(MushroomEvent.SortMushroom(usageType))
                                },
                                colors = RadioButtonDefaults.colors(
                                    unselectedColor = Color.Gray,
                                    disabledSelectedColor = Color.LightGray,
                                    disabledUnselectedColor = Color.LightGray
                                )
                            )
                            Text(
                                text = czechLabels[usageType.name] ?: usageType.name,
                                color = Color.DarkGray,
                                fontSize = (state.fontSize*0.8).sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // mushroom list
            items(state.mushrooms) { mushroom ->
                val highlightColor = when (mushroom.usage) {
                    UsageType.Jedla -> Color(0xFFDCEDC8)
                    UsageType.Nejedla -> Color(0xFFFFE0B2)
                    UsageType.Jedovata -> Color(0xFFFFCDD2)
                    else -> Color(0xFFF5F5F5)
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .background(highlightColor, RoundedCornerShape(8.dp))
                        .clickable { onMushroomClick(mushroom.id) }
                        .padding(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = mushroom.imageID),
                            contentDescription = "Image of ${mushroom.name}",
                            modifier = Modifier
                                .size(80.dp)
                                .padding(end = 16.dp)
                        )

                        Column(
                            modifier = Modifier.weight(0.85f)
                        ) {
                            Text(
                                text = mushroom.name,
                                color = Color.DarkGray,
                                fontSize = (state.fontSize).sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                softWrap = true,
                            )
                        }
                        Text(
                            text = "›",
                            color = Color.Gray,
                            fontSize = (state.fontSize * 1.5).sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .weight(0.15f)
                                .padding(horizontal = 8.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.End
                        )
                    }
                }
            }
        }
    }
}