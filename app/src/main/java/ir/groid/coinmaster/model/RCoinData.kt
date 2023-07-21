package ir.groid.coinmaster.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.groid.coinmaster.util.Constans.TableCoin
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = TableCoin)
data class RCoinData(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    val img: String? = null,
    val txtCoinName: String? = null,
    val txtTaghir: Double? = null,
    val txtPrice: String? = null,
    val txtMarketCap: String? = null

) : Parcelable
