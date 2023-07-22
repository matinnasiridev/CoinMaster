package ir.groid.coinmaster.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.subjects.BehaviorSubject
import ir.groid.coinmaster.model.RCoinData
import ir.groid.coinmaster.model.RNewsData
import ir.groid.coinmaster.repository.AppRepository

class MarketVM(private val repository: AppRepository) : ViewModel() {
    private val dis = CompositeDisposable()
    fun getAllNews(): LiveData<List<RNewsData>> = repository.getAllNews()

    fun refreshNews(): Completable = repository.refreshNews()

    fun getAllCoins(): LiveData<List<RCoinData>> = repository.getAllCoins()

    fun progressStatus(): BehaviorSubject<Boolean> = repository.progressBarSubject

    fun refreshCoins(): Completable = repository.refreshCoins()

    fun addDis(d: Disposable) = dis.add(d)

    override fun onCleared() {
        super.onCleared()
        dis.dispose()
    }
}