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
        val changeDataType: (n: NewsData) -> List<RNewsData> = {
            val list = ArrayList<RNewsData>()
            it.data.forEach { news ->
                list.add(RNewsData(txtNews = news.title, urlNews = news.url))
            }
            list
        }
        return apiService
            .getTopNews()
            .map { changeDataType(it) }
            .doOnSuccess { newsDao.insertNews(it) }
            .ignoreElement()
    }

    /**
     * Coins
     */
    fun getAllCoins(): LiveData<List<RCoinData>> = coinDao.getAll()

    val shimmerSubject = BehaviorSubject.create<Boolean>()

    fun refreshCoins(): Completable {
        val changeDataType: (n: CoinsData) -> List<RCoinData> = {
            val list = ArrayList<RCoinData>()
            it.data.forEach { coin ->
                list.add(
                    RCoinData(
                        id = coin.coinInfo.id.toLong(),
                        img = coin.coinInfo.imageUrl,
                        txtCoinName = coin.coinInfo.name,
                        txtTaghir = coin.dISPLAY.uSD.cHANGEPCTHOUR,
                        txtPrice = coin.dISPLAY.uSD.pRICE,
                        txtMarketCap = coin.dISPLAY.uSD.mKTCAP,
                        fullName = coin.coinInfo.fullName,
                        open = coin.dISPLAY.uSD.oPEN24HOUR,
                        todayHigh = coin.dISPLAY.uSD.hIGH24HOUR,
                        todayLow = coin.dISPLAY.uSD.lOW24HOUR,
                        changeToday = coin.dISPLAY.uSD.cHANGE24HOUR,
                        Algorithm = coin.coinInfo.algorithm,
                        totalVolume = coin.dISPLAY.uSD.tOTALVOLUME24H,
                        marketCap = coin.dISPLAY.uSD.mKTCAP,
                        supply = coin.dISPLAY.uSD.sUPPLY
                    )
                )
            }
            list
        }

        shimmerSubject.onNext(true)

        return apiService
            .getTopCoins()
            .map { changeDataType(it) }
            .doOnSuccess {
                coinDao.insert(it)
                shimmerSubject.onNext(false)
            }
            .ignoreElement()
    }
}