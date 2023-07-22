package ir.groid.coinmaster.di

import android.content.Context
import androidx.room.Room
import ir.groid.coinmaster.database.AppDatabase
import ir.groid.coinmaster.database.CoinDao
import ir.groid.coinmaster.database.NewsDao
import ir.groid.coinmaster.model.RCoinData
import ir.groid.coinmaster.model.RNewsData
import ir.groid.coinmaster.util.Constans.DatabaseName
import org.koin.dsl.module

object DatabaseM {
    val DatabaseM = module {

        single { provideDatabase(get()) }
        factory { provideCoinDao(get()) }
        factory { provideNewsDao(get()) }
        factory { provideRCoinData() }
        factory { provideRNewsData() }
    }

    private fun provideDatabase(c: Context): AppDatabase {
        return Room.databaseBuilder(
            c,
            AppDatabase::class.java,
            DatabaseName
        ).allowMainThreadQueries()
            .build()
    }

    private fun provideCoinDao(db: AppDatabase): CoinDao = db.coinDao


    private fun provideNewsDao(db: AppDatabase): NewsDao = db.newsDao


    private fun provideRCoinData(): RCoinData = RCoinData()

    private fun provideRNewsData(): RNewsData = RNewsData()
}