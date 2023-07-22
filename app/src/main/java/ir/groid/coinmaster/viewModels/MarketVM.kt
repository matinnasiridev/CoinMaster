package ir.groid.coinmaster.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.subjects.BehaviorSubject
import ir.groid.coinmaster.model.RCoinData
import ir.groid.coinmaster.model.RNewsData
import ir.groid.coinmaster.repository.AppRepository

class MarketVM(private val repository: AppRepository) : ViewModel() {

    fun getAllNews(): LiveData<List<RNewsData>> = repository.getAllNews()

    fun refreshNews(): Completable = repository.refreshNews()

    fun getAllCoins(): LiveData<List<RCoinData>> = repository.getAllCoins()

    fun progressStatus(): BehaviorSubject<Boolean> = repository.progressBarSubject

    fun refreshCoins(): Completable = repository.refreshCoins()


}