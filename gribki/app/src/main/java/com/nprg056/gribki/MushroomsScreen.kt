package com.nprg056.gribki

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun MushroomScreen(
    state: MushroomState,
    onEvent: (MushroomEvent)->Unit
){
    Scaffold (){padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
        ) {
            item{
                Row (
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    SortType.values().forEach { sortType->
                        Row(
                            modifier = Modifier.clickable {
                                onEvent(MushroomEvent.SortMushroom(sortType))
                            },
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            RadioButton(selected = state.sortType ==sortType,
                                onClick = {
                                    onEvent(MushroomEvent.SortMushroom(sortType))
                                }
                            )
                            Text(text = sortType.name)
                        }
                    }
                }
            }
            items(state.mushrooms
            ){mushroom->
                Row (
                    modifier = Modifier.fillMaxWidth()
                ){
                    Column(
                    ){
                        Text(
                            text = "${mushroom.name}"
                        )
                        Image(
                            painter = painterResource(id = mushroom.imageID),
                            contentDescription = "Mushroom Image"
                        )
                    }

                }

            }
        }
    }
}