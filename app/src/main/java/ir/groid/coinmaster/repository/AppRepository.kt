package ir.groid.coinmaster.repository


import androidx.lifecycle.LiveData
import io.reactivex.Completable
import io.reactivex.subjects.BehaviorSubject
import ir.groid.coinmaster.api.ApiService
import ir.groid.coinmaster.database.CoinDao
import ir.groid.coinmaster.database.NewsDao
import ir.groid.coinmaster.model.RCoinData
import ir.groid.coinmaster.model.RNewsData
import ir.groid.coinmaster.responce.CoinsData
import ir.groid.coinmaster.responce.NewsData


class AppRepository(
    private val apiService: ApiService,
    private val coinDao: CoinDao,
    private val newsDao: NewsDao
) {


    /**
     * News
     */
    fun getAllNews(): LiveData<List<RNewsData>> = newsDao.getAllNews()

    fun refreshNews(): Completable {
        val changeDataType: (n: NewsData) -> ArrayList<RNewsData> = {
            val list = ArrayList<RNewsData>()
            it.data.forEach { news ->
                list.add(RNewsData(txtNews = news.title, urlNews = news.url))
            }
            list
        }

        return apiService
            .getTopNews()
            .doOnSuccess { newsDao.insertNews(changeDataType(it)) }
            .ignoreElement()
    }

    /**
     * Coins
     */
    fun getAllCoins(): LiveData<List<RCoinData>> = coinDao.getAll()

    val progressBarSubject = BehaviorSubject.create<Boolean>()

    fun refreshCoins(): Completable {
        val changeDataType: (n: CoinsData) -> ArrayList<RCoinData> = {
            val list = ArrayList<RCoinData>()
            it.data.forEach { coin ->
                list.add(RCoinData())
            }
            list
        }
        progressBarSubject.onNext(true)
        return apiService
            .getTopCoins()
            .doOnSuccess { coinDao.insert(changeDataType(it)) }
            .doFinally { progressBarSubject.onNext(false) }
            .ignoreElement()
    }



}