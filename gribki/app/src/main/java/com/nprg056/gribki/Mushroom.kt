import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nprg056.gribki.UsageType


@Entity
data class Mushroom(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val desc: String,
    val loc: String,
    val usage: UsageType,
    val image: Int

)
