package ir.groid.coinmaster.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.groid.coinmaster.util.Constans.TableNews

@Entity(tableName = TableNews)
data class RNewsData(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    val txtNews: String? = null,
    val urlNews: String? = null
)
