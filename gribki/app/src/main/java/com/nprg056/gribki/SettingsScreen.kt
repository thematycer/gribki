package com.nprg056.gribki

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScreenSetting(
    onEvent: (MushroomEvent)->Unit,
    state: MushroomState,
    fontSize: Float,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onQuitClick: () -> Unit
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .background(Color(0xFFE74C3C), RoundedCornerShape(8.dp))
                .clickable { onQuitClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Ukončit aplikaci",
                color = Color.White,
                fontSize = (state.fontSize).sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
    }
}