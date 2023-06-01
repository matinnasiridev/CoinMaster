package ir.groid.coinmaster

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import ir.groid.coinmaster.adapter.ChartAdapter
import ir.groid.coinmaster.apiManager.ALL
import ir.groid.coinmaster.apiManager.ApiManager
import ir.groid.coinmaster.apiManager.HOUR
import ir.groid.coinmaster.apiManager.HOURS24
import ir.groid.coinmaster.apiManager.MONTH
import ir.groid.coinmaster.apiManager.MONTH3
import ir.groid.coinmaster.apiManager.WEEK
import ir.groid.coinmaster.apiManager.YEAR
import ir.groid.coinmaster.apiManager.model.ChartData
import ir.groid.coinmaster.apiManager.model.CoinsAboutItem
import ir.groid.coinmaster.apiManager.model.CoinsData
import ir.groid.coinmaster.databinding.ActivityCoinDataBinding

class CoinDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinDataBinding
    private lateinit var dataCoin: CoinsData.Data
    private lateinit var dataAbout: CoinsAboutItem
    private val apiManager = ApiManager()

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
        binding.layoutChart.txtChartChanger1.text = " " + dataCoin.dISPLAY.uSD.cHANGEPCT24HOUR + " "


        val taghir = dataCoin.rAW.uSD.cHANGEPCT24HOUR
        if (taghir > 0) {
            binding.layoutChart.txtChartChange2.text =
                dataCoin.rAW.uSD.cHANGEPCT24HOUR.toString().substring(0, 4) + "%"
            binding.layoutChart.txtChartChange2.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorGain
                )
            )
            binding.layoutChart.txtChartUpdown.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorGain
                )
            )
            binding.layoutChart.txtChartUpdown.text = "▲"
            binding.layoutChart.sparkmain.lineColor = ContextCompat.getColor(
                binding.root.context,
                R.color.colorGain
            )
        } else if (taghir < 0) {
            binding.layoutChart.txtChartChange2.text =
                dataCoin.rAW.uSD.cHANGEPCT24HOUR.toString().substring(0, 4) + "%"
            binding.layoutChart.txtChartChange2.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorLoss
                )
            )
            binding.layoutChart.txtChartUpdown.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorLoss
                )
            )
            binding.layoutChart.txtChartUpdown.text = "▼"
            binding.layoutChart.sparkmain.lineColor = ContextCompat.getColor(
                binding.root.context,
                R.color.colorLoss
            )
        } else {
            binding.layoutChart.txtChartUpdown.text = "▶"
            binding.layoutChart.sparkmain.lineColor = ContextCompat.getColor(
                binding.root.context,
                R.color.primaryTextColor
            )
            binding.layoutChart.txtChartChange2.text = "0%"
            binding.layoutChart.txtChartUpdown.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.primaryTextColor
                )
            )
            binding.layoutChart.txtChartChange2.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.primaryTextColor
                )
            )
        }

        var period: String = HOUR
        runChart(period)

        binding.layoutChart.radio.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_12h -> {
                    period = HOUR
                }

                R.id.radio_1d -> {
                    period = HOURS24
                }

                R.id.radio_1w -> {
                    period = WEEK
                }

                R.id.radio_1m -> {
                    period = MONTH
                }

                R.id.radio_3m -> {
                    period = MONTH3
                }

                R.id.radio_1y -> {
                    period = YEAR
                }

                R.id.radio_all -> {
                    period = ALL
                }
            }
            runChart(period)
        }

        binding.layoutChart.sparkmain.setScrubListener {
            if (it == null) {
                binding.layoutChart.txtChartPrice.text = dataCoin.dISPLAY.uSD.pRICE
            } else {
                binding.layoutChart.txtChartPrice.text =
                    "$" + (it as ChartData.Data).close.toString()
            }
        }
    }

    private fun runChart(p: String) {
        apiManager.getChartData(
            dataCoin.coinInfo.name,
            p,
            object : ApiManager.ApiCallBack<Pair<List<ChartData.Data>, ChartData.Data?>> {
                override fun onSuccess(data: Pair<List<ChartData.Data>, ChartData.Data?>) {

                    val chartAdapter = ChartAdapter(data.first, data.second?.open.toString())
                    binding.layoutChart.sparkmain.adapter = chartAdapter

                }

                override fun onError(errorMassage: String) {
                    Log.v("ChartDate", errorMassage)
                }

            }
        )
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