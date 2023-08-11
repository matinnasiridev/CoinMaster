package ir.groid.coinmaster.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.groid.coinmaster.databinding.ActivityCoinDataBinding
import ir.groid.coinmaster.model.RCoinAbout
import ir.groid.coinmaster.model.RCoinData
import ir.groid.coinmaster.util.Constans.CENTERKEY
import ir.groid.coinmaster.util.Constans.KEYONE
import ir.groid.coinmaster.util.Constans.KEYTWO
import ir.groid.coinmaster.util.lunch
import ir.groid.coinmaster.viewModels.CoinDataVM
import org.koin.android.ext.android.inject


class CoinDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinDataBinding
    private val viewM: CoinDataVM by inject()
    private lateinit var cd: RCoinData
    private lateinit var ca: RCoinAbout
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCoinDataBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getDataFromBundle()
        initUI()
    }

    private fun getDataFromBundle() {
        val get = intent.getBundleExtra(CENTERKEY)!!
        cd = get.getParcelable(KEYONE)!!
        ca = get. getParcelable(KEYTWO)!!
    }

    private fun initUI() {
        header()
        chart()
        statistics()
        about()
    }


    private fun header() {
        binding.apply {
            layoutToolbar.toolbar.title = cd.fullName
        }
    }

    private fun chart() {
        binding.layoutChart.apply {
            txtChartPrice.text = cd.txtPrice
        }
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
            txtTwitter.lunch("https://twitter.com/${ca.coinTwitter}")
            txtRaddit.lunch(ca.coinReddit)
            txtGithub.lunch(ca.coinGithub)
            txtAboutCoin.text = ca.coinDes
        }
    }
}