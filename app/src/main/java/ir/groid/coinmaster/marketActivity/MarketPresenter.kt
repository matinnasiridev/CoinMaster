package ir.groid.coinmaster.marketActivity

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import ir.groid.coinmaster.api.ApiManager
import ir.groid.coinmaster.model.RCoinData
import ir.groid.coinmaster.model.RNewsData
import ir.groid.coinmaster.responce.CoinsAboutData
import ir.groid.coinmaster.responce.CoinsAboutItem
import ir.groid.coinmaster.responce.CoinsData
import ir.groid.coinmaster.util.ApiCallBack
import ir.groid.coinmaster.util.Constans.ERROR_HANDELER

class MarketPresenter :
    MarketContract.Presenter {

    private var centerView: MarketContract.View? = null

    // Fix It
    private lateinit var apiManager: ApiManager

    override fun onAttach(view: MarketContract.View): String {
        this.centerView = view

        getNewsData((1..49).random())
        getCoinsData()

        return "Market"
    }

    private fun getNewsData(range: Int) {
        apiManager.getNews(object : ApiCallBack<ArrayList<RNewsData>> {
            override fun onSuccess(data: ArrayList<RNewsData>) {
                centerView!!.showNews(data, range)
            }

            override fun onError(em: String) {
                Log.v(ERROR_HANDELER, "fun getNewsData ~> $em")
            }
        })
    }

    private fun getCoinsData() {
        apiManager.getCoinList(object : ApiCallBack<List<RCoinData>> {
            override fun onSuccess(data: List<RCoinData>) {
                centerView!!.showCoins(data)
            }

            override fun onError(em: String) {
                Log.v(ERROR_HANDELER, "fun getCoinsData ~> $em")
            }
        })
    }


    override fun onDetach() {
        centerView = null
    }

    override fun onRefresh() {
        getNewsData((1..49).random())
        getCoinsData()
    }

    override fun onCoinClick(context: Context): MutableMap<String, CoinsAboutItem> {

        val abouteMap: MutableMap<String, CoinsAboutItem>?
        val fileInString = context.assets
            .open("currencyinfo.json")
            .bufferedReader()
            .use { it.readText() }

        abouteMap = mutableMapOf()

        val gson = Gson()
        val dataAboutAll = gson.fromJson(fileInString, CoinsAboutData::class.java)

        dataAboutAll.forEach {
            abouteMap[it.currencyName!!] = CoinsAboutItem(
                it.info?.web,
                it.info?.github,
                it.info?.twt,
                it.info?.desc,
                it.info?.reddit,
            )
        }
        return abouteMap
    }
}