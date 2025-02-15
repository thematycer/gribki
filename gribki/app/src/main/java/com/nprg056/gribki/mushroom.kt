import androidx.room.vo.PrimaryKey

data class Mushroom(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val desc: String,
    val loc: String,
    val usage: UsageType,
    val image: Int

)
enum class UsageType{
    //muzeme pridat chutna, smrtelna, ...
    jedla,
    nejedla,
    jedovata
}