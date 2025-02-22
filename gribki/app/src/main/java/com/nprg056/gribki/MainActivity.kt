package com.nprg056.gribki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first


class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            MushroomDatabase::class.java,
            "mushroom_database.db"
        )
            .build()
    }
    private val viewModel: MushroomViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MushroomViewModel(db.MushroomDao()) as T
            }
        }
    }

    private suspend fun populateDatabase() {
        val existingMushrooms = db.MushroomDao().searchMushroomsByName("").first()
        if (existingMushrooms.isEmpty()) {
            val mushrooms = listOf(
                Mushroom(
                    name = "Hřib smrkový",
                    desc = "Jedlá houba s hnědým kloboukem",
                    loc = "V smrkových lesích",
                    usage = UsageType.Jedla,
                    imageID = 1 // TODO
                ),
                Mushroom(
                    name = "Muchomůrka červená",
                    desc = "Jedovatá houba s červeným kloboukem",
                    loc = "Listnaté a smíšené lesy",
                    usage = UsageType.Jedovata,
                    imageID = 2
                ),
                Mushroom(
                    name = "Bedla vysoká",
                    desc = "Chutná jedlá houba",
                    loc = "Louky a pastviny",
                    usage = UsageType.Jedla,
                    imageID = 3
                )
            )

            mushrooms.forEach { mushroom ->
                db.MushroomDao().insert(mushroom)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch(Dispatchers.IO) {
            populateDatabase()
        }

        setContent {
            val state by viewModel.state.collectAsState()
            MushroomScreen(state = state, onEvent = viewModel::onEvent)
        }
    }
}

