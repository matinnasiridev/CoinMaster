package ir.groid.coinmaster

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ir.groid.coinmaster.apiManager.model.CoinsAboutItem
import ir.groid.coinmaster.apiManager.model.CoinsData
import ir.groid.coinmaster.databinding.ActivityCoinDataBinding

class CoinDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinDataBinding
    private lateinit var dataCoin: CoinsData.Data
    private lateinit var dataAbout: CoinsAboutItem

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCoinDataBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val allData = intent.getBundleExtra("dataOmade")!!
        dataCoin = allData.getParcelable("bundle1")!!
        dataAbout = allData.getParcelable("bundle2")!!
        binding.layoutToolbar.toolbar.title = dataCoin.coinInfo.fullName
        initUi()
    }

    private fun initUi() {
        initChartUi()
        initStatisticsUi()
        initAboutUi()
    }

    private fun initChartUi() {

        binding.layoutChart.txtChartPrice.text = dataCoin.dISPLAY.uSD.pRICE


    }

    @SuppressLint("SetTextI18n")
    private fun initStatisticsUi() {

        // Base Link
        val layout = binding.layoutStatistics
        val raw = dataCoin.dISPLAY.uSD

        // Open
        layout.txtOpen.text = raw.oPEN24HOUR

        // To Day's High
        layout.txtTodayHi.text = raw.hIGH24HOUR

        // To Day's Low
        layout.txtTodayLow.text = raw.lOW24HOUR

        // Change To Day's
        layout.txtChange.text = raw.cHANGE24HOUR

        // Algorithm
        layout.txtAlgorithm.text = dataCoin.coinInfo.algorithm

        // Total Volume
        layout.txtTotalVolume.text = raw.tOTALVOLUME24H

        // Market Cap
        layout.txtMarketCap.text = raw.mKTCAP

        // Supply
        layout.txtSupply.text = raw.sUPPLY

    }

    @SuppressLint("SetTextI18n")
    private fun initAboutUi() {

        val layout = binding.layoutAbout

        if (dataAbout.coinWebsite == "") {
            layout.txtWebsite.text = "No Data!"
        } else {
            layout.txtWebsite.text = dataAbout.coinWebsite
        }

        if (dataAbout.coinGithub == "") {
            layout.txtGithub.text = "No Data!"
        } else {
            layout.txtGithub.text = dataAbout.coinGithub
        }

        if (dataAbout.coinReddit == "") {
            layout.txtRaddit.text = "No Data!"
        } else {
            layout.txtRaddit.text = dataAbout.coinReddit
        }

        if (dataAbout.coinTwitter == "") {
            layout.txtTwitter.text = "No Data!"
        } else {
            layout.txtTwitter.text = "@" + dataAbout.coinTwitter
        }

        if (dataAbout.coinDes == "") {
            layout.txtAboutCoin.text = "There is no explanation!"
        } else {
            layout.txtAboutCoin.text = dataAbout.coinDes
        }

        layout.txtWebsite.setOnClickListener {
            if (dataAbout.coinWebsite != "") {
                openOnWeb(dataAbout.coinWebsite!!)
            }
        }
        layout.txtGithub.setOnClickListener {
            if (dataAbout.coinGithub != "") {
                openOnWeb(dataAbout.coinGithub!!)
            }
        }
        layout.txtRaddit.setOnClickListener {
            if (dataAbout.coinReddit != "") {
                openOnWeb(dataAbout.coinReddit!!)
            }
        }
        layout.txtTwitter.setOnClickListener {
            if (dataAbout.coinTwitter != "") {
                openOnWeb("https://twitter.com/" + dataAbout.coinTwitter!!)
            }
        }

    }

    private fun openOnWeb(url: String) {
        if (url.isEmpty()) {
            Toast.makeText(this, "Link Is Empty", Toast.LENGTH_SHORT).show()
        } else {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }
}