package ir.groid.coinmaster.marketActivity

import android.content.Context
import ir.groid.coinmaster.model.CoinsAboutItem
import ir.groid.coinmaster.model.CoinsData


interface MarketContract {

    interface Presenter {

        fun onAttach(view: View): String
        fun onDetach()
        fun onRefresh()
        fun onCoinClick(context: Context): MutableMap<String, CoinsAboutItem>

    }

    interface View {

        fun showNews(newsData: ArrayList<Pair<String, String>>, random: Int)
        fun showCoins(coinsData: List<CoinsData.Data>)

    }
}