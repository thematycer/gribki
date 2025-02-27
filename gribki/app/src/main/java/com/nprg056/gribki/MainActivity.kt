package com.nprg056.gribki

import android.content.Context
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
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
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
        applicationContext.deleteDatabase("mushroom_database.db")//need to deleta database to get data updated for now
        val existingMushrooms = db.MushroomDao().searchMushroomsByName("").first()
        if (existingMushrooms.isEmpty()) {
            val mushrooms = listOf(
                Mushroom(
                    name = "Hřib smrkový",
                    desc = "Jedlá houba s hnědým kloboukem",
                    loc = "V smrkových lesích",
                    usage = UsageType.Jedla,
                    imageID = R.mipmap.hrib_smrkovy_foreground
                ),
                Mushroom(
                    name = "Muchomůrka červená",
                    desc = "Jedovatá houba s červeným kloboukem",
                    loc = "Listnaté a smíšené lesy",
                    usage = UsageType.Jedovata,
                    imageID = R.mipmap.mochomurka_cervena_foreground
                ),
                Mushroom(
                    name = "Bedla vysoká",
                    desc = "Chutná jedlá houba",
                    loc = "Louky a pastviny",
                    usage = UsageType.Jedla,
                    imageID = R.mipmap.bedla_vysoka_foreground
                ) ,
                Mushroom(
                    name = "Mycena alba",
                    desc = "Klobouk je až 1,5 cm široký, tupě kuželovitý, na temeni bledorůžový, směrem k okraji bledší a prosvítavě rýhovaný. Lupeny bělavé až slabě nažloutlé nebo narůžovělé. Třeň bělavý až slabě nažloutlý, dutý, křehký. Chuť a vůně nevýrazná.",
                    loc = "Vyrůstá nehojně, jednotlivě nebo v menších skupinách koncem léta a na podzim na nehnojených mechatých loukách.",
                    usage = UsageType.Nejedla,
                    imageID = R.mipmap.mycena_alba_foreground
                ),
                Mushroom(
                    name = "Čechratka černohuňatá",
                    desc = "Je dobře poznatelná podle hnědého klobouku, sbíhavých žlutých lupenů a černě huňatého tlustého třeně. Klobouky dosahují velikosti až 20 cm v průměru.",
                    loc = "Čechratka černohuňatá je velice hojná houba jehličnatých lesů, kde vyrůstá od července do listopadu na pařezech a kořenech smrků.",
                    usage = UsageType.Nejedla,
                    imageID = R.mipmap.cechratka_cernohunata_foreground
                ),
                Mushroom(
                    name = "Hnojník inkoustový",
                    desc = "Na stříbřitém klobouku, který je v mládí vejčitého tvaru a později se jeho okraje postupně obloukovitě zvedají, mívá hnědé šupinky. Lupeny jsou velmi husté. Ve stáří se plodnice roztékají do černé inkoustové kaše (autolýza).",
                    loc = "Hnojník inkoustový roste dosti hojně od července do listopadu na prosvětlených travnatých místech bohatších na dusík. Objevuje se zpravidla v trsech na loukách, v parcích, zahradách, ale i v lesích. Vyrůstá přímo ze země nebo z tlejícího dřeva či dřevních zbytků.",
                    usage = UsageType.Jedla,
                    imageID = R.mipmap.hnojnik_inkoustovy_foreground
                ),
                Mushroom(
                    name = "Muchomůrka jízlivá",
                    desc = " Klobouk je až 7 cm široký, dlouho vejčitý, s bílou až nažloutou, hladkou pokožkou. Okraj klobouku v mládí spojuje se třeněm bílá plachetka, později tvořící prsten. Lupeny jsou husté, bílé. Třeň je bílý, válcovitý, na bázi rozšířený v kulovitou hlízu obalenou bílou blanitou pochvou, někdy skrytou v substrátu. Povrch třeně je typicky vláknitě šupinatý.",
                    loc = "Vyrůstá od července do října především v jehličnatých, ale i v listnatých lesích pod smrky, jedlemi a buky. Objevuje se zejména ve vyšších polohách, na kyselých půdách.",
                    usage = UsageType.Jedovata,
                    imageID = R.mipmap.muchomurka_jizliva_foreground
                ),
                Mushroom(
                    name = "Líha srostlá",
                    desc = " Plodnice líhy srostlé jsou čistě bíle zbarvené, jemně ojíněné. Klobouk je vyklenutý až mírně prohloubený, v mládí s podvinutým okrajem. Krémově zbarvené lupeny jsou nízké, široce připojené až mírně sbíhavé. Třeň je bílý až krémový, vločkatě vláknitý. Dužnina je pružná, s nasládlou vůní, přirovnávanou k vůni dymnivky (Corydalis). Plodnice dosahují výšky do 10 cm, šířka klobouku se pohybuje od 2 do 10 cm.",
                    loc = "Líha srostlá se vyskytuje od července do listopadu v trsech na zemi v lesích i mimo les. Vyhovují jí ruderální a na dusík bohatá místa, jako jsou okraje cest a porosty kopřiv. Dokonce jsem ji našla, jak prorůstá vrstvou asfaltu na starší lesní cestě.",
                    usage = UsageType.Jedovata,
                    imageID = R.mipmap.liha_srostla_foreground
                ),
                Mushroom(
                    name = "Měkkouš kadeřavý",
                    desc = "Je nápadný měkkými mušlovitými plodnicemi, které mají na spodní straně zkadeřené bělavé lištny. Svrchní strana je jemně plstnatá, hnědá, směrem k okraji světlejší až téměř bílá. Plodnice dosahují velikosti do 2,5 cm.",
                    loc = "Měkkouš kadeřavý vyrůstá po celý rok na mrtvém dřevě různých listnáčů, velice často buků a bříz. Upřednostňuje vyšší polohy.",
                    usage = UsageType.Nejedla,
                    imageID = R.mipmap.mekkous_kaderavy_foreground
                ),
                /*
                Mushroom(
                    name = "",
                    desc = "",
                    loc = "",
                    usage = UsageType.Jedovata,
                    imageID = R.mipmap.muchomurka_jizliva_foreground
                ),*/

            )

            mushrooms.forEach { mushroom ->
                db.MushroomDao().insert(mushroom)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        loadData()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch(Dispatchers.IO) {
            populateDatabase()
        }

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
                        onSaveClick = {saveData(state)}
                    )
                }
            }
        }

    }
    private fun saveData(state: MushroomState){
        val fontSize = state.fontSize
        val sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("fontSize", fontSize)
        editor.apply()
        Toast.makeText(this, "Nastavení bylo uloženo", Toast.LENGTH_SHORT).show()
    }
    private fun loadData(){
        val sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE)
        val fontSize = sharedPreferences.getFloat("fontSize", 16f)
        viewModel.onEvent(MushroomEvent.ChangeFontSize(fontSize))
    }

}

