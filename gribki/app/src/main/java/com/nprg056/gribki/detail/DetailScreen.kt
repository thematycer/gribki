package com.nprg056.gribki.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nprg056.gribki.database.Mushroom
import com.nprg056.gribki.R
import com.nprg056.gribki.database.UsageType

@Composable
fun MushroomDetailScreen(
    mushroomId: Int,
    mushroom: Mushroom?,
    onEvent: (MushroomDetailEvent) -> Unit,
    onBackClick: () -> Unit,
    fontSize: Float
) {
    LaunchedEffect(key1 = mushroomId) {
        if (mushroomId != 0) {
            onEvent(MushroomDetailEvent.LoadMushroom(mushroomId))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
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
                            fontSize = (fontSize * 1.2).sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )
                        Text(
                            text = " Zpět",
                            fontSize = (fontSize).sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.DarkGray
                        )
                    }
                }
            }

            mushroom?.let {
                val (backgroundColor, statusText, statusColor) = when (it.usage) {
                    UsageType.Jedla ->
                        Triple(Color(0xFFDCEDC8), "Jedlá", Color(0xFF33691E))
                    UsageType.Nejedla ->
                        Triple(Color(0xFFFFE0B2), "Nejedlá", Color(0xFFE65100))
                    UsageType.Jedovata ->
                        Triple(Color(0xFFFFCDD2), "Jedovatá", Color(0xFFB71C1C))
                    else -> Triple(Color(0xFFF5F5F5), "", Color.Gray)
                }

                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(scrollState)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(backgroundColor, RoundedCornerShape(12.dp))
                            .padding(16.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = it.name,
                                color = Color.DarkGray,
                                fontSize = (fontSize * 1.5).sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            if (statusText.isNotEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .padding(bottom = 16.dp)
                                        .background(
                                            color = statusColor.copy(alpha = 0.2f),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .padding(horizontal = 12.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = statusText,
                                        color = statusColor,
                                        fontSize = (fontSize).sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .background(Color.White, RoundedCornerShape(8.dp))
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
                                            color = statusColor,
                                            fontSize = (fontSize * 0.9).sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.Serif
                                        )
                                        Text(
                                            text = content,
                                            color = Color.DarkGray,
                                            fontSize = (fontSize).sp,
                                            fontFamily = FontFamily.Serif,
                                            modifier = Modifier.padding(top = 4.dp)
                                        )
                                    }
                                }
                                InfoSection("Popis", it.desc)
                                InfoSection("Lokalita", it.loc)
                            }
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
                    Image(
                        painter = painterResource(id = R.drawable.icon_alert_triangle),
                        contentDescription = "Icon with alert triangle",
                    )
                    Text(
                        text = "Houba nebyla nalezena!",
                        color = Color.Red,
                        fontSize = (fontSize).sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                }
            }
        }
    }
}