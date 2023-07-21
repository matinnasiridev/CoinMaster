package ir.groid.coinmaster.repository


import ir.groid.coinmaster.api.ApiService
import ir.groid.coinmaster.database.CoinDao

class AppRepository(private val apiService: ApiService, private val coinDao: CoinDao) {

}