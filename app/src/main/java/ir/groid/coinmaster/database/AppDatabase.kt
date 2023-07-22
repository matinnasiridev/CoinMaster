package ir.groid.coinmaster.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.groid.coinmaster.model.RCoinData
import ir.groid.coinmaster.model.RNewsData

@Database(
    [RCoinData::class, RNewsData::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val coinDao: CoinDao
    abstract val newsDao: NewsDao
}