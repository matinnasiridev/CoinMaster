package ir.groid.coinmaster.marketActivity

import android.content.Context
import ir.groid.coinmaster.model.RCoinData
import ir.groid.coinmaster.model.RNewsData
import ir.groid.coinmaster.responce.CoinsAboutItem
import ir.groid.coinmaster.responce.CoinsData


interface MarketContract {

    interface Presenter {

        fun onAttach(view: View): String
        fun onDetach()
        fun onRefresh()
        fun onCoinClick(context: Context): MutableMap<String, CoinsAboutItem>

    }

    interface View {

        fun showNews(newsData: ArrayList<RNewsData>, random: Int)
        fun showCoins(coinsData: List<RCoinData>)

    }
}