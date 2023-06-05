package ir.groid.coinmaster.marketActivity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.LinearLayoutManager
import ir.groid.coinmaster.coinDataActivity.CoinDataActivity
import ir.groid.coinmaster.model.model.*
import ir.groid.coinmaster.databinding.ActivityMarketBinding

class MarketActivity : AppCompatActivity(), MarketContract.View, MarketAdapter.RecCallBack {

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

    override fun showNews(newsData: ArrayList<Pair<String, String>>, random: Int) {
        binding.newsMarket.txtNews.setOnClickListener {
            mPresenter.onRefresh()
        }
        binding.newsMarket.txtNews.text = newsData[random].first
        binding.newsMarket.imgNews.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(newsData[random].second)))
        }
    }

    override fun showCoins(coinsData: List<CoinsData.Data>) {
        binding.resMarket.recyclerItemMarket.adapter = MarketAdapter(coinsData, this)
        binding.resMarket.recyclerItemMarket.layoutManager = LinearLayoutManager(this)
    }

    override fun onTouch(data: CoinsData.Data) {
        val intent = Intent(this, CoinDataActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("bundle1", data)
        bundle.putParcelable(
            "bundle2",
            mPresenter.onCoinClick(this)[data.coinInfo.name] ?: CoinsAboutItem()
        )
        intent.putExtra("dataOmade", bundle)
        startActivity(intent)
    }
}