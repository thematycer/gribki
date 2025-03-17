package com.nprg056.gribki

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            MushroomDatabase::class.java,
            "mushroom_database.db"
        ).build()
    }

    private val repository by lazy {
        MushroomRepository(db.MushroomDao(), applicationContext)
    }

    private val viewModel: MushroomViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MushroomViewModel(repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            val state by viewModel.state.collectAsState()

            NavHost(navController = navController, startDestination = "mushroom_list") {
                composable("mushroom_list") {
                    MushroomScreen(
                        state = state,
                        onEvent = viewModel::onEvent,
                        onMushroomClick = { id -> navController.navigate("mushroom_detail/$id") },
                        screenSettingClick = { navController.navigate("settings") }
                    )
                }
                composable(
                    route = "mushroom_detail/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) { backStackEntry ->
                    val mushroomId = backStackEntry.arguments?.getInt("id") ?: 0
                    val mushroom = state.mushrooms.find { it.id == mushroomId }

                    MushroomDetailScreen(
                        mushroom = mushroom,
                        state = state,
                        onBackClick = { navController.navigate("mushroom_list") }
                    )
                }
                composable("settings") {
                    ScreenSetting(
                        fontSize = state.fontSize,
                        onEvent = viewModel::onEvent,
                        state = state,
                        onBackClick = { navController.navigate("mushroom_list") },
                        onSaveClick = {
                            viewModel.saveFontSettings()
                            Toast.makeText(this@MainActivity, "Nastavení bylo uloženo", Toast.LENGTH_SHORT).show()
                        },
                        onQuitClick = { finishAffinity() }
                    )
                }
            }
        }
    }
}