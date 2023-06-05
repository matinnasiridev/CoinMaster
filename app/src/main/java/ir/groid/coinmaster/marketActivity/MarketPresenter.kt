package ir.groid.coinmaster.marketActivity

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import ir.groid.coinmaster.model.ApiManager
import ir.groid.coinmaster.model.ERROR_HANDELER
import ir.groid.coinmaster.model.model.CoinsAboutData
import ir.groid.coinmaster.model.model.CoinsAboutItem
import ir.groid.coinmaster.model.model.CoinsData

class MarketPresenter :
    MarketContract.Presenter {

    private var centerView: MarketContract.View? = null
    private val apiManager = ApiManager()

    override fun onAttach(view: MarketContract.View): String {
        this.centerView = view

        getNewsData((1..49).random())
        getCoinsData()

        return "Market"
    }

    private fun getNewsData(range: Int) {
        apiManager.getNews(object : ApiManager.ApiCallBack<ArrayList<Pair<String, String>>> {
            override fun onSuccess(data: ArrayList<Pair<String, String>>) {
                centerView!!.showNews(data, range)
            }

            override fun onError(errorMassage: String) {
                Log.v(ERROR_HANDELER, "fun getNewsData ~> $errorMassage")
            }
        })
    }

    private fun getCoinsData() {
        apiManager.getCionsList(object : ApiManager.ApiCallBack<List<CoinsData.Data>> {
            override fun onSuccess(data: List<CoinsData.Data>) {
                centerView!!.showCoins(data)
            }

            override fun onError(errorMassage: String) {
                Log.v(ERROR_HANDELER, "fun getCoinsData ~> $errorMassage")
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