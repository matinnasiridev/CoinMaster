package ir.groid.coinmaster.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.groid.coinmaster.responce.RCoinData

@Database(
    [RCoinData::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val coinDao: CoinDao
}