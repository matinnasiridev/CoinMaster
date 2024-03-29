package ir.groid.coinmaster.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import ir.groid.coinmaster.R
import ir.groid.coinmaster.adapter.ChartAdapter
import ir.groid.coinmaster.databinding.ActivityCoinDataBinding
import ir.groid.coinmaster.di.AppService
import ir.groid.coinmaster.model.RCoinAbout
import ir.groid.coinmaster.model.RCoinData
import ir.groid.coinmaster.util.CheckChangeClass
import ir.groid.coinmaster.util.Constans.BASE_URL_IMAG
import ir.groid.coinmaster.util.Constans.BASE_URL_TWITT
import ir.groid.coinmaster.util.Constans.CENTERKEY
import ir.groid.coinmaster.util.Constans.KEYONE
import ir.groid.coinmaster.util.Constans.KEYTWO
import ir.groid.coinmaster.util.Constans.StanderCKEY
import ir.groid.coinmaster.util.Constans.TAG
import ir.groid.coinmaster.util.lunch
import ir.groid.coinmaster.util.setP
import ir.groid.coinmaster.util.thereadHandeler
import ir.groid.coinmaster.viewModels.CoinDataVM
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class CoinDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinDataBinding
    private val viewM by viewModel<CoinDataVM>()
    private val imageLoader by inject<AppService.ImageLoader>()
    private val ads by inject<AppService.AdsSystem>()
    private val charAdapter = ChartAdapter()
    private lateinit var cd: RCoinData
    private lateinit var ca: RCoinAbout
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCoinDataBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getDataFromBundle()
        adsLoader()
        initUI()
    }

    private fun getDataFromBundle() {
        val get = intent.getBundleExtra(CENTERKEY)!!
        cd = get.getParcelable(KEYONE)!!
        ca = get.getParcelable(KEYTWO)!!
    }

    private fun adsLoader() {
        ads.standardBanner(this, StanderCKEY, binding.standardBanner)
    }

    private fun initUI() {
        header()
        chart()
        statistics()
        about()
    }


    private fun header() {
        binding.toolbarMarket.apply {
            ibT.isVisible = true
            ivT.isVisible = true
            ibT.setOnClickListener { onBackPressed() }
            imageLoader.loader(BASE_URL_IMAG + cd.img, ivT)
            tvT.text = cd.fullName
        }
    }

    private fun chart() {
        binding.layoutChart.apply {
            txtChartPrice.text = cd.txtPrice
            txtChartChange2.text = setP(cd.txtTaghir)

            viewM.changeFilter(cd.txtTaghir?.toDouble()) {
                when (it) {
                    CheckChangeClass.HIGH -> {
                        setViewsColor(R.color.colorGain)
                        txtChartUpdown.setImageResource(R.drawable.ic_tre_up)
                    }

                    CheckChangeClass.LOW -> {
                        setViewsColor(R.color.colorLoss)
                        txtChartUpdown.setImageResource(R.drawable.ic_tre_dw)
                    }

                    CheckChangeClass.NORMAL -> {
                        setViewsColor(R.color.secondaryTextColor)
                        txtChartUpdown.setImageResource(R.drawable.ic_tre_zr)
                    }
                }
            }

            sparkmain.adapter = charAdapter

            getPositions { charAdapter.submit(it) }
            radio.setOnCheckedChangeListener { _, checkedId ->
                getPositions(checkedId) { charAdapter.submit(it) }
            }
        }
    }

    private val setViewsColor: (Int) -> Unit = {
        binding.layoutChart.apply {
            sparkmain.lineColor = ContextCompat.getColor(
                binding.root.context,
                it
            )

            txtChartChange2.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    it
                )
            )
            txtChartUpdown.setColorFilter(
                ContextCompat.getColor(
                    binding.root.context,
                    it
                )
            )
        }
    }

    private fun getPositions(id: Int? = R.id.radio_12h, job: (List<Double>) -> Unit) {
        viewM.getChartData(cd.txtCoinName!!, id).thereadHandeler()
            .subscribe(object : SingleObserver<List<Double>> {
                override fun onSubscribe(d: Disposable) {
                    viewM.getDis(d)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, e.message!!)
                }

                override fun onSuccess(t: List<Double>) {
                    job(t)
                }

            })
    }

    private fun statistics() {
        binding.layoutStatistics.apply {
            txtOpen.text = cd.open
            txtTodayHi.text = cd.todayHigh
            txtTodayLow.text = cd.todayLow
            txtChange.text = cd.changeToday
            txtAlgorithm.text = cd.Algorithm
            txtTotalVolume.text = cd.totalVolume
            txtMarketCap.text = cd.marketCap
            txtSupply.text = cd.supply
        }
    }

    private fun about() {
        binding.layoutAbout.apply {
            txtWebsite.lunch(ca.coinWebsite)
            txtTwitter.lunch(BASE_URL_TWITT + ca.coinTwitter)
            txtRaddit.lunch(ca.coinReddit)
            txtGithub.lunch(ca.coinGithub)
            txtAboutCoin.text = ca.coinDes
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ads.destroyStandardBanner(StanderCKEY, binding.standardBanner)
    }
}