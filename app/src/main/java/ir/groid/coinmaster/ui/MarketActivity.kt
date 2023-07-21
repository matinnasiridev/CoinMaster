package ir.groid.coinmaster.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import ir.groid.coinmaster.adapter.MarketAdapter
import ir.groid.coinmaster.databinding.ActivityMarketBinding
import ir.groid.coinmaster.marketActivity.MarketContract
import ir.groid.coinmaster.marketActivity.MarketPresenter
import ir.groid.coinmaster.model.RCoinData
import ir.groid.coinmaster.model.RNewsData
import ir.groid.coinmaster.responce.CoinsAboutItem
import ir.groid.coinmaster.util.fillManager

class MarketActivity : AppCompatActivity(), MarketContract.View,
    MarketAdapter.RecCallBack<RCoinData> {

    private lateinit var binding: ActivityMarketBinding
    private val mPresenter: MarketContract.Presenter = MarketPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarketBinding.inflate(layoutInflater)
        binding.toolbarMarket.toolbar.title = mPresenter.onAttach(this)
        setContentView(binding.root)

        binding.swiper.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                binding.swiper.isRefreshing = false
                mPresenter.onRefresh()
            }, 1500)
        }
    }

    override fun onResume() {
        super.onResume()
        mPresenter.onRefresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    override fun showNews(newsData: ArrayList<RNewsData>, random: Int) {
        binding.newsMarket.apply {
            txtNews.setOnClickListener {
                mPresenter.onRefresh()
            }
            txtNews.text = newsData[random].txtNews
            imgNews.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(newsData[random].urlNews)))
            }
        }
    }

    override fun showCoins(coinsData: List<RCoinData>) {
        binding.resMarket.recyclerItemMarket.fillManager {
            MarketAdapter(coinsData, this)
        }
    }

    override fun onTouch(data: RCoinData) {
        val intent = Intent(this, CoinDataActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("bundle1", data)
        bundle.putParcelable(
            "bundle2",
            mPresenter.onCoinClick(this)[data.txtCoinName] ?: CoinsAboutItem()
        )
        intent.putExtra("dataOmade", bundle)
        startActivity(intent)
    }
}