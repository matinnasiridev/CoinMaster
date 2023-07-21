package ir.groid.coinmaster.responce

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.groid.coinmaster.util.Constans.TableCoin

@Entity(tableName = TableCoin)
data class RCoinData(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val txtTitle: String? = null
)
