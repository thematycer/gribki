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
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.nprg056.gribki.factory.MushroomDetailViewModelFactory
import com.nprg056.gribki.factory.MushroomListViewModelFactory
import com.nprg056.gribki.factory.SettingsViewModelFactory
import com.nprg056.gribki.mushroomList.MushroomListViewModel
import com.nprg056.gribki.mushroomList.MushroomScreen
import com.nprg056.gribki.settings.ScreenSetting
import com.nprg056.gribki.settings.SettingsEvent
import com.nprg056.gribki.settings.SettingsViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val db by lazy {
        MushroomDatabase.getInstance(applicationContext)
    }


    private val repository by lazy {
        MushroomRepository(db.MushroomDao(), applicationContext)
    }

//    private val listViewModel: MushroomListViewModel by viewModels {
//        MushroomListViewModelFactory(repository)
//    }
//
//    private val detailViewModel: MushroomDetailViewModel by viewModels {
//        MushroomDetailViewModelFactory(repository)
//    }
//
//    private val settingsViewModel: SettingsViewModel by viewModels {
//        SettingsViewModelFactory(repository)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            repository.populateDatabase()
        }
        setContent {
            val navController = rememberNavController()
//            val listState by listViewModel.state.collectAsState()
//            val settingsState by settingsViewModel.state.collectAsState()
//            val detailState by detailViewModel.state.collectAsState()

            NavHost(navController = navController, startDestination = "mushroom_list") {
                composable("mushroom_list") {
                    val listViewModel: MushroomListViewModel =
                        viewModel(
                        factory = MushroomListViewModelFactory(repository)
                    )
                    val listState by listViewModel.state.collectAsState()
                    val settingsViewModel: SettingsViewModel =
                        viewModel(
                        factory = SettingsViewModelFactory(repository)
                    )
                    val settingsState by settingsViewModel.state.collectAsState()

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
                    val detailViewModel: MushroomDetailViewModel = viewModel(
                        factory = MushroomDetailViewModelFactory(repository)
                    )
                    val detailState by detailViewModel.state.collectAsState()

                    val settingsViewModel: SettingsViewModel = viewModel(
                        factory = SettingsViewModelFactory(repository)
                    )
                    val settingsState by settingsViewModel.state.collectAsState()
                    val mushroomId = backStackEntry.arguments?.getInt("id") ?: 0
                    detailViewModel.onEvent(MushroomDetailEvent.LoadMushroom(mushroomId))

                    MushroomDetailScreen(
                        mushroomId = mushroomId,
                        mushroom = detailState.selectedMushroom,
                        fontSize = settingsState.fontSize,
                        onEvent = detailViewModel::onEvent,
                        onBackClick = { navController.navigate("mushroom_list") }
                    )
                }
                composable("settings") {
                    val settingsViewModel: SettingsViewModel = viewModel(
                        factory = SettingsViewModelFactory(repository)
                    )
                    val settingsState by settingsViewModel.state.collectAsState()
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