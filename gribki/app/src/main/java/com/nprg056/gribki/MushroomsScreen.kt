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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.TextStyle
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.res.painterResource


@Composable
fun MushroomScreen(
    state: MushroomState,
    onEvent: (MushroomEvent)->Unit,
    onMushroomClick: (Int) -> Unit
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
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Atlas Hub",
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Radio Button Bar
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
//                        .background(Color.LightGray)
//                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UsageType.entries.forEach { usageType ->
                        Row(
                            modifier = Modifier
                                .clickable {
                                    onEvent(MushroomEvent.SortMushroom(usageType))
                                },
//                                 .padding(horizontal = 8.dp),
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
                                text = usageType.name,
                                color = Color.DarkGray,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
            items(state.mushrooms
            ){mushroom->
                Row (
                    modifier = Modifier.fillMaxWidth()
                        .clickable { onMushroomClick(mushroom.id) }
                        .padding(padding)
                ){

                    Text(
                        text = "${mushroom.name}",
                        color = Color.Blue,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier.padding(8.dp)
                    )

                    Image(
                        painter = painterResource(id = mushroom.imageID),
                        contentDescription = "Image of ${mushroom.name}",
                        modifier = Modifier.padding(8.dp).size(100.dp)
                    )

                }

            }
        }
    }
}



@Composable
fun MushroomDetailScreen(
    mushroom: Mushroom?,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "<- zpatky",
            modifier = Modifier
                .clickable { onBackClick() }
                .padding(8.dp),
            color = Color.Blue,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
        )

        mushroom?.let {
            Image(
                painter = painterResource(id = it.imageID),
                contentDescription = "Image of ${it.name}",
                modifier = Modifier.size(150.dp)
            )
            Text(
                text = "Name: ${it.name}",
                color = Color.Blue,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.padding(8.dp)
                )
            Text(
                text = "Popis: ${it.desc}",
                color = Color.Blue,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.padding(8.dp)
                )
            Text(
                text = "Lokalita: ${it.loc}",
                color = Color.Blue,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.padding(8.dp)
                )
            Text(
                text = "Použití: : ${it.usage}",
                color = Color.Blue,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.padding(8.dp)
            )
        } ?: Text(
            text = "Houba nebyla nalezena!",
            color = Color.Red,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            modifier = Modifier.padding(8.dp)
        )
    }
}

