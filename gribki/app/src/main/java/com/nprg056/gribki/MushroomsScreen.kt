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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Slider
import androidx.compose.ui.res.painterResource


@Composable
fun MushroomScreen(
    state: MushroomState,
    onEvent: (MushroomEvent) -> Unit,
    onMushroomClick: (Int) -> Unit,
    screenSettingClick: () -> Unit
){
    Scaffold() { padding ->
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
                        .padding(16.dp), //.clickable { screenSettingClick() },
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
                    label = { Text("Název houby:") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )

                // mushroom edibility radio button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
//                        .background(Color.LightGray)
//                        .padding(8.dp),
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
                                    //selectedColor = Color.Green,
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
                val highlightColor = when {
                    mushroom.usage.toString().contains("jedla", ignoreCase = true) -> Color(0xFFDCEDC8)
                    mushroom.usage.toString().contains("nejedla", ignoreCase = true) -> Color(0xFFFFE0B2)
                    mushroom.usage.toString().contains("jedovata", ignoreCase = true) -> Color(0xFFFFCDD2)
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

                        Column {
                            Text(
                                text = mushroom.name,
                                color = Color.DarkGray,
                                fontSize = (state.fontSize).sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif
                            )
                        }
                        Text(
                            text = "›",
                            color = Color.Gray,
                            fontSize = (state.fontSize * 1.5).sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.End
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun MushroomDetailScreen(
    mushroom: Mushroom?,
    onBackClick: () -> Unit,
    state: MushroomState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))
            .padding(16.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                    .clickable { onBackClick() }
                    .padding(horizontal = 12.dp, vertical = 8.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "←",
                        fontSize = (state.fontSize * 1.2).sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                    Text(
                        text = " Zpět",
                        fontSize = (state.fontSize).sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.DarkGray
                    )
                }
            }
        }
        mushroom?.let {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = it.name,
                        color = Color.DarkGray,
                        fontSize = (state.fontSize * 1.5).sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Box(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = it.imageID),
                            contentDescription = "Image of ${it.name}",
                            modifier = Modifier
                                .size(200.dp)
                                .padding(8.dp)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        @Composable
                        fun InfoSection(label: String, content: String) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                Text(
                                    text = label,
                                    color = Color(0xFF4CAF50),
                                    fontSize = (state.fontSize * 0.9).sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Serif
                                )
                                Text(
                                    text = content,
                                    color = Color.DarkGray,
                                    fontSize = (state.fontSize).sp,
                                    fontFamily = FontFamily.Serif,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }

                        InfoSection("Popis", it.desc)
                        InfoSection("Lokalita", it.loc)
                        InfoSection("Použití", it.usage.toString())
                    }
                }
            }
        } ?: Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "⚠️",
                    fontSize = (state.fontSize * 2).sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Houba nebyla nalezena!",
                    color = Color.Red,
                    fontSize = (state.fontSize).sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
            }
        }
    }
}

@Composable
fun ScreenSetting(
    onEvent: (MushroomEvent)->Unit,
    state: MushroomState,
    fontSize: Float,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))
            .padding(16.dp)
            .statusBarsPadding()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                    .clickable { onBackClick() }
                    .padding(horizontal = 12.dp, vertical = 8.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "←",
                        fontSize = (state.fontSize * 1.2).sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                    Text(
                        text = " Zpět",
                        fontSize = (state.fontSize).sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.DarkGray
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF90EE90), RoundedCornerShape(8.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Nastavení",
                fontSize = (state.fontSize * 1.5).sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
                .background(Color.White, RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Aktuální velikost písma",
                    color = Color(0xFF4CAF50),
                    fontSize = (state.fontSize * 0.9).sp,
                    fontWeight = FontWeight.Bold
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Ukázkový text - ${fontSize.toInt()} sp",
                        fontSize = fontSize.sp,
                        color = Color.DarkGray
                    )
                }

                Text(
                    text = "Změnit velikost písma",
                    color = Color(0xFF4CAF50),
                    fontSize = (state.fontSize * 0.9).sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 8.dp)
                        .align(Alignment.Start)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "12",
                        fontSize = (state.fontSize * 0.8).sp,
                        color = Color.Gray
                    )
                    Slider(
                        value = fontSize,
                        onValueChange = { newSize ->
                            onEvent(MushroomEvent.ChangeFontSize(newSize))
                        },
                        valueRange = 12f..30f,
                        steps = 6,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                    )
                    Text(
                        text = "30",
                        fontSize = (state.fontSize * 0.8).sp,
                        color = Color.Gray
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .background(Color(0xFF4CAF50), RoundedCornerShape(8.dp))
                .clickable { onSaveClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Uložit nastavení",
                color = Color.White,
                fontSize = (state.fontSize).sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
    }
}