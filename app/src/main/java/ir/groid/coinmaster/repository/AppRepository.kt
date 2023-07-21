package ir.groid.coinmaster.repository

import ir.groid.coinmaster.api.ApiService
import ir.groid.coinmaster.database.CoinDao
import ir.groid.coinmaster.database.NewsDao


class AppRepository(
    private val apiService: ApiService,
    private val coinDao: CoinDao,
    private val newsDao: NewsDao
) {


}