package com.nprg056.gribki

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import androidx.core.content.edit
import com.nprg056.gribki.database.Mushroom
import com.nprg056.gribki.database.MushroomDao
import com.nprg056.gribki.database.UsageType

class MushroomRepository(
    private val dao: MushroomDao,
    private val context: Context
) {
    fun searchMushroomsByName(name: String): Flow<List<Mushroom>> {
        return dao.searchMushroomsByName(name)
    }

    fun getMushroomByNameAndUsage(name: String, usageType: UsageType): Flow<List<Mushroom>> {
        return dao.getMushroomByNameAndUsage(name, usageType)
    }

    fun getMushroomById(id: Int): Flow<Mushroom> {
        return dao.getMushroomById(id)
    }

    suspend fun populateDatabase() {
        withContext(Dispatchers.IO) {
            //context.deleteDatabase("mushroom_database.db")
            val existingMushrooms = dao.searchMushroomsByName("").first()
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
                    ),
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
                )

                mushrooms.forEach { mushroom ->
                    dao.insert(mushroom)
                }
            }
        }
    }

    fun loadFontSize(): Float {
        val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        return sharedPreferences.getFloat("fontSize", 16f)
    }

    fun saveFontSize(fontSize: Float) {
        val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putFloat("fontSize", fontSize)
            apply()
        }
    }


}