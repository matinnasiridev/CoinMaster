package ir.groid.coinmaster.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.groid.coinmaster.model.RCoinData
import ir.groid.coinmaster.util.Constans.TableCoin

@Dao
interface CoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(listData: List<RCoinData>)

    @Query("SELECT * FROM $TableCoin")
    fun getAll(): LiveData<List<RCoinData>>
}