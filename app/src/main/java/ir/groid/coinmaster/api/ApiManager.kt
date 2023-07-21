package ir.groid.coinmaster.api


import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ir.groid.coinmaster.model.RCoinData
import ir.groid.coinmaster.model.RNewsData
import ir.groid.coinmaster.responce.ChartData
import ir.groid.coinmaster.responce.CoinsData
import ir.groid.coinmaster.responce.NewsData
import ir.groid.coinmaster.util.ApiCallBack
import ir.groid.coinmaster.util.Constans.ALL
import ir.groid.coinmaster.util.Constans.HISTO_DAY
import ir.groid.coinmaster.util.Constans.HISTO_HOUR
import ir.groid.coinmaster.util.Constans.HISTO_MINUTE
import ir.groid.coinmaster.util.Constans.HOUR
import ir.groid.coinmaster.util.Constans.HOURS24
import ir.groid.coinmaster.util.Constans.MONTH
import ir.groid.coinmaster.util.Constans.MONTH3
import ir.groid.coinmaster.util.Constans.WEEK
import ir.groid.coinmaster.util.Constans.YEAR

class ApiManager(
    private val apiService: ApiService
) {

    fun getNews(apiCallBack: ApiCallBack<ArrayList<RNewsData>>) {
        apiService
            .getTopNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<NewsData> {
                override fun onSubscribe(d: Disposable) {}

                override fun onError(e: Throwable) {
                    apiCallBack.onError(e.message!!)
                }

                override fun onSuccess(t: NewsData) {
                    val data = t.data
                    val dataToSend: ArrayList<RNewsData> = arrayListOf()
                    data.forEach {
                        dataToSend.add(RNewsData(txtNews = it.title, urlNews = it.url))
                    }
                    apiCallBack.onSuccess(dataToSend)
                }

            })
    }

    fun getCoinList(apiCallBack: ApiCallBack<List<RCoinData>>) {

        apiService
            .getTopCoins()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<CoinsData> {
                override fun onSubscribe(d: Disposable) {}

                override fun onError(e: Throwable) {
                    apiCallBack.onError(e.message!!)
                }

                override fun onSuccess(t: CoinsData) {
                    val arra: ArrayList<RCoinData> = arrayListOf()
                    t.data.forEach {
                        arra.add(
                            RCoinData(
                                img = it.coinInfo.imageUrl,
                                txtCoinName = it.coinInfo.fullName,
                                txtMarketCap = it.rAW.uSD.mARKET,
                                txtPrice = it.dISPLAY.uSD.pRICE,
                                txtTaghir = it.rAW.uSD.cHANGEPCT24HOUR
                            )
                        )
                    }
                    apiCallBack.onSuccess(arra)
                }

            })
    }

    fun getChartData(
        symbol: String,
        period: String,
        apiCallback: ApiCallBack<Pair<List<ChartData.Data>, ChartData.Data?>>
    ) {

        var histoPeriod = ""
        var limit = 30
        var aggregate = 1

        when (period) {

            HOUR -> {
                histoPeriod = HISTO_MINUTE
                limit = 60
                aggregate = 12
            }

            HOURS24 -> {
                histoPeriod = HISTO_HOUR
                limit = 24
            }

            MONTH -> {
                histoPeriod = HISTO_DAY
                limit = 30
            }

            MONTH3 -> {
                histoPeriod = HISTO_DAY
                limit = 90
            }

            WEEK -> {
                histoPeriod = HISTO_HOUR
                aggregate = 6
            }

            YEAR -> {
                histoPeriod = HISTO_DAY
                aggregate = 13
            }

            ALL -> {
                histoPeriod = HISTO_DAY
                aggregate = 30
                limit = 2000
            }

        }


        apiService
            .getChartData(histoPeriod, symbol, limit, aggregate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ChartData> {
                override fun onSubscribe(d: Disposable) {}

                override fun onError(e: Throwable) {
                    apiCallback.onError(e.message!!)
                }

                override fun onSuccess(t: ChartData) {
                    apiCallback.onSuccess(Pair(t.data, t.data.maxByOrNull { it.close.toFloat() }))
                }

            })


    }
}
