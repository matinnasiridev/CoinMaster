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

    val img: String? = null, // coininfo Imageurl
    val txtCoinName: String? = null, // CoinInfo Name
    val txtTaghir: String? = null, // CHANGEPCTHOUR
    val txtPrice: String? = null, // display usd price
    val txtMarketCap: String? = null, // dis usd MKTCAP
    val fullName: String? = null // coin info Internal

) : Parcelable
