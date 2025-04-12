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
import com.nprg056.gribki.database.MushroomDatabase
import com.nprg056.gribki.detail.MushroomDetailEvent
import com.nprg056.gribki.detail.MushroomDetailScreen
import com.nprg056.gribki.detail.MushroomDetailViewModel
import com.nprg056.gribki.mushroomList.MushroomListViewModel
import com.nprg056.gribki.mushroomList.MushroomScreen
import com.nprg056.gribki.settings.ScreenSetting
import com.nprg056.gribki.settings.SettingsEvent
import com.nprg056.gribki.settings.SettingsViewModel

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

    private val listViewModel: MushroomListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MushroomListViewModel(repository) as T
            }
        }
    }

    private val detailViewModel: MushroomDetailViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MushroomDetailViewModel(repository) as T
            }
        }
    }

    private val settingsViewModel: SettingsViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SettingsViewModel(repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            val listState by listViewModel.state.collectAsState()
            val settingsState by settingsViewModel.state.collectAsState()
            val detailState by detailViewModel.state.collectAsState()

            NavHost(navController = navController, startDestination = "mushroom_list") {
                composable("mushroom_list") {
                    MushroomScreen(
                        state = listState,
                        fontSize = settingsState.fontSize,
                        onEvent = listViewModel::onEvent,
                        onMushroomClick = { id -> navController.navigate("mushroom_detail/$id") },
                        screenSettingClick = { navController.navigate("settings") }
                    )
                }
                composable(
                    route = "mushroom_detail/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) { backStackEntry ->
                    val mushroomId = backStackEntry.arguments?.getInt("id") ?: 0
                    detailViewModel.onEvent(MushroomDetailEvent.LoadMushroom(mushroomId))

                    MushroomDetailScreen(
                        mushroom = detailState.selectedMushroom,
                        fontSize = settingsState.fontSize,
                        onBackClick = { navController.navigate("mushroom_list") }
                    )
                }
                composable("settings") {
                    ScreenSetting(
                        onEvent = settingsViewModel::onEvent,
                        state = settingsState,
                        onBackClick = { navController.navigate("mushroom_list") },
                        onSaveClick = {
                            settingsViewModel.onEvent(SettingsEvent.SaveSettings)
                            Toast.makeText(this@MainActivity, "Nastavení bylo uloženo", Toast.LENGTH_SHORT).show()
                        },
                        onQuitClick = { finishAffinity() }
                    )
                }
            }
        }
    }
}