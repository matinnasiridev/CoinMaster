package ir.groid.coinmaster.ui


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.groid.coinmaster.adapter.MarketAdapter
import ir.groid.coinmaster.databinding.ActivityMarketBinding
import ir.groid.coinmaster.model.RCoinData
import ir.groid.coinmaster.model.RNewsData
import ir.groid.coinmaster.util.Constans.KEY
import ir.groid.coinmaster.util.RecyclerEvent
import ir.groid.coinmaster.util.setAdapter
import ir.groid.coinmaster.util.showToast
import ir.groid.coinmaster.viewModels.MarketVM
import org.koin.android.ext.android.inject


class MarketActivity : AppCompatActivity(), RecyclerEvent<RCoinData> {

    private lateinit var binding: ActivityMarketBinding
    private lateinit var mAdapter: MarketAdapter
    private val viewM: MarketVM by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.toolbarMarket.toolbar.title = "Market"
        mAdapter = MarketAdapter(this)

        news()
        showRecycler()


    }

    // News

    private fun news() = viewM.getAllNews().observe(this) { bindNews(it[setRandom(it.size)]) }


    private val setRandom: (max: Int) -> Int = { (0 until it).random() }


    private fun bindNews(r: RNewsData) {
        binding.newsMarket.apply {
            txtNews.text = r.txtNews
            txtNews.setOnClickListener { news() }
            imgNews.setOnClickListener {
                showToast("Open News ${r.urlNews}")
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com")))
            }
        }
    }

    // Coins

    private fun showRecycler() {
        binding.resMarket.recyclerItemMarket.setAdapter { mAdapter }
    }

    private fun refreshRecycler(l: List<RCoinData>) = mAdapter.refreshRecycler(l)

    override fun onClick(data: RCoinData) {
        val bundle = Bundle()
        bundle.putParcelable(KEY, data)
        startActivity(Intent(this, CoinDataActivity::class.java), bundle)
    }
}