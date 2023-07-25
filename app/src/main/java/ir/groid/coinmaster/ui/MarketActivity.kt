package ir.groid.coinmaster.ui


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import ir.groid.coinmaster.adapter.MarketAdapter
import ir.groid.coinmaster.databinding.ActivityMarketBinding
import ir.groid.coinmaster.model.RCoinData
import ir.groid.coinmaster.model.RNewsData
import ir.groid.coinmaster.util.Constans.BtnMore
import ir.groid.coinmaster.util.Constans.KEY
import ir.groid.coinmaster.util.Constans.TAG
import ir.groid.coinmaster.util.RecyclerEvent
import ir.groid.coinmaster.util.setAdapter
import ir.groid.coinmaster.util.theredHandeler
import ir.groid.coinmaster.viewModels.MarketVM
import org.koin.android.ext.android.inject
import kotlin.system.measureTimeMillis


class MarketActivity : AppCompatActivity(), RecyclerEvent<RCoinData> {

    private lateinit var binding: ActivityMarketBinding
    private var isCoinEmpty: Boolean = true
    private val viewM: MarketVM by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarMarket.toolbar.title = "Market"

        swiperRefresh()
        manageProgress()
        onMoreClick()

        refreshNews()
        news()

        refreshCoins()
        coins()
    }


    private fun swiperRefresh() {
        binding.swiper.setOnRefreshListener {
            Handler(Looper.myLooper()!!).postDelayed({
                binding.swiper.isRefreshing = false
            }, measureTimeMillis {
                refreshCoins()
                refreshNews()
            })
        }
    }

    private fun manageProgress() {
        viewM.addDis(viewM
            .progressStatus()
            .subscribe {
                runOnUiThread {
                    binding.apply {
                        if (it && !isCoinEmpty) {
                            progress.isVisible = true
                            resMarket.resContainer.isVisible = false
                            creatorName.isVisible = false
                        } else {
                            progress.isVisible = false
                            resMarket.resContainer.isVisible = true
                            creatorName.isVisible = true
                        }
                    }
                }
            }
        )
    }

    private fun onMoreClick() {
        binding.resMarket.btnMore.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(BtnMore)))
        }
    }

    // News
    private fun refreshNews() {
        viewM
            .refreshNews()
            .theredHandeler()
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
            .theredHandeler()
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
                binding.resMarket.recyclerItemMarket.setAdapter {
                    MarketAdapter(this, it)
                }
                isCoinEmpty = false
            } else {
                viewM.tCreateFakeCoins()
            }
        }
    }

    override fun onClick(coinData: RCoinData) {
        val bundle = Bundle()
        bundle.putParcelable(KEY, viewM.getAboutData(this))
        startActivity(Intent(this, CoinDataActivity::class.java))
    }
}