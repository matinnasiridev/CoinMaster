package ir.groid.coinmaster.ui

import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import ir.groid.coinmaster.databinding.ActivityCoinDataBinding
import ir.groid.coinmaster.model.RCoinAbout
import ir.groid.coinmaster.model.RCoinData
import ir.groid.coinmaster.util.Constans.BASE_URL_IMAG
import ir.groid.coinmaster.util.Constans.BASE_URL_TWITT
import ir.groid.coinmaster.util.Constans.CENTERKEY
import ir.groid.coinmaster.util.Constans.KEYONE
import ir.groid.coinmaster.util.Constans.KEYTWO
import ir.groid.coinmaster.util.Constans.TAG
import ir.groid.coinmaster.util.load
import ir.groid.coinmaster.util.lunch
import ir.groid.coinmaster.viewModels.CoinDataVM
import org.koin.androidx.viewmodel.ext.android.viewModel


class CoinDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinDataBinding
    private val viewM by viewModel<CoinDataVM>()
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
        ca = get.getParcelable(KEYTWO)!!
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
            ivT.load(BASE_URL_IMAG + cd.img)
            tvT.text = cd.fullName
        }
    }

    private fun chart() {
        binding.layoutChart.apply {
            txtChartPrice.text = cd.txtPrice

            radio.setOnCheckedChangeListener { _, checkedId ->
                Log.d(TAG,viewM.getPeriod(checkedId))
            }
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
            txtTwitter.lunch(BASE_URL_TWITT + ca.coinTwitter)
            txtRaddit.lunch(ca.coinReddit)
            txtGithub.lunch(ca.coinGithub)
            txtAboutCoin.text = ca.coinDes
        }
    }
}