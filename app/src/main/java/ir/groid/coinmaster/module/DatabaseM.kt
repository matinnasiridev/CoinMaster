package ir.groid.coinmaster.module

import android.content.Context
import androidx.room.Room
import ir.groid.coinmaster.database.AppDatabase
import ir.groid.coinmaster.database.CoinDao
import ir.groid.coinmaster.responce.RCoinData
import ir.groid.coinmaster.util.Constans.DatabaseName
import org.koin.dsl.module

object DatabaseM {
    val DatabaseM = module {

        single { provideDatabse(get()) }
        single { provideCoinDao(get()) }
        factory { provideRCoinData() }
    }

    private fun provideDatabse(c: Context): AppDatabase {
        return Room.databaseBuilder(
            c,
            AppDatabase::class.java,
            DatabaseName
        ).build()
    }

    private fun provideCoinDao(db: AppDatabase): CoinDao = db.coinDao


    private fun provideRCoinData(): RCoinData = RCoinData()
}