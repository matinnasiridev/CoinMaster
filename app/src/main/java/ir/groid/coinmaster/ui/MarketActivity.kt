package ir.groid.coinmaster.ui


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import ir.groid.coinmaster.adapter.MarketAdapter
import ir.groid.coinmaster.databinding.ActivityMarketBinding
import ir.groid.coinmaster.model.RCoinData
import ir.groid.coinmaster.model.RNewsData
import ir.groid.coinmaster.util.Constans.BtnMore
import ir.groid.coinmaster.util.Constans.CENTERKEY
import ir.groid.coinmaster.util.Constans.KEYONE
import ir.groid.coinmaster.util.Constans.KEYTWO
import ir.groid.coinmaster.util.Constans.TAG
import ir.groid.coinmaster.util.RecyclerEvent
import ir.groid.coinmaster.util.open
import ir.groid.coinmaster.util.setAdapter
import ir.groid.coinmaster.util.showToast
import ir.groid.coinmaster.util.thereadHandeler
import ir.groid.coinmaster.viewModels.MarketVM
import org.koin.android.ext.android.inject
import kotlin.system.measureTimeMillis


class MarketActivity : AppCompatActivity(), RecyclerEvent<RCoinData> {

    private lateinit var binding: ActivityMarketBinding
    private val viewM: MarketVM by inject()
    private lateinit var cAdapter: MarketAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initService()
        initUI()
    }

    private fun initService() {
        cAdapter = MarketAdapter(this)

        viewM.internetConnected(this) {
            refresh()
        }
    }

    private fun initUI() {
        swiper()
        onMoreClick()
        fillRv()
        shimmerManage()
        refresh()

        // News
        news()

        // Coins
        coins()
    }

    private fun shimmerManage() {
        viewM.addDis(viewM.shimmerStatus().subscribe {
            runOnUiThread {
                cAdapter.refreshActive(!it)
            }
        })
    }


    private fun refresh() {
        refreshCoins()
        refreshNews()
    }

    private fun fillRv() = binding.resMarket.recyclerItemMarket.setAdapter {
        cAdapter
    }

    private fun swiper() {
        binding.swiper.setOnRefreshListener {
            Handler(Looper.myLooper()!!).postDelayed({
                binding.swiper.isRefreshing = false
            }, measureTimeMillis {
                refresh()
            })
        }
    }

    private fun onMoreClick() {
        binding.resMarket.btnMore.open(BtnMore)
    }

    // News
    private fun refreshNews() {
        viewM
            .refreshNews()
            .thereadHandeler()
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    viewM.addDis(d)
                }

                override fun onComplete() {
                    news()
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, e.message!!)
                }
            })
    }

    private fun news() {
        viewM.getAllNews().observe(this) {
            if (it.isNotEmpty()) {
                bindNews(it[setRandom(it.size)])
            }
        }
    }


    private val setRandom: (max: Int) -> Int = { (0 until it).random() }

    private fun bindNews(r: RNewsData) {
        binding.newsMarket.apply {
            txtNews.text = r.txtNews
            txtNews.setOnClickListener { news() }
            imgNews.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(r.urlNews)))
            }
        }
    }

    // Coins
    private fun refreshCoins() {
        viewM
            .refreshCoins()
            .thereadHandeler()
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    viewM.addDis(d)
                }

                override fun onComplete() {
                    coins()
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, e.message!!)
                }
            })
    }

    private fun coins() {
        viewM.getAllCoins().observe(this) {
            if (it.isNotEmpty()) {
                cAdapter.submit(it)
                fillRv()
            }
        }
    }

    override fun onClick(coinData: RCoinData) {
        val bundle = Bundle()
        bundle.putParcelable(KEYONE, coinData)
        bundle.putParcelable(KEYTWO, viewM.getAboutDataByName(this, coinData.txtCoinName!!))
        startActivity(Intent(this, CoinDataActivity::class.java).putExtra(CENTERKEY, bundle))
    }
}