package ir.groid.coinmaster

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import ir.groid.coinmaster.adapter.MarketAdapter
import ir.groid.coinmaster.apiManager.ApiManager
import ir.groid.coinmaster.apiManager.ERROR_HANDELER
import ir.groid.coinmaster.apiManager.model.CoinsAboutData
import ir.groid.coinmaster.apiManager.model.CoinsAboutItem
import ir.groid.coinmaster.apiManager.model.CoinsData
import ir.groid.coinmaster.databinding.ActivityMarketBinding

class MarketActivity : AppCompatActivity(), MarketAdapter.RecCallBack {

    private lateinit var binding: ActivityMarketBinding
    private val apiManager = ApiManager()
    private lateinit var dataNews: ArrayList<Pair<String, String>>
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var aboutDataMap: MutableMap<String, CoinsAboutItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMarketBinding.inflate(layoutInflater)
        binding.toolbarMarket.toolbar.title = "Market"
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MarketData", Context.MODE_PRIVATE)
        sharedPreferences
        getAboutDataFromAssets()
        editor = sharedPreferences.edit()

        // Put Data To SharedPreferences
        putData()

        // Set Action For Swiper
        binding.swiper.setOnRefreshListener {
            initUi()
            Handler(Looper.getMainLooper()).postDelayed({
                binding.swiper.isRefreshing = false
            }, 1500)
        }

    }

    override fun onResume() {
        super.onResume()
        // Inut Ui In onResume
        initUi()

        // Show Swiper
        binding.swiper.isRefreshing = true
        Handler(Looper.getMainLooper()).postDelayed({
            binding.swiper.isRefreshing = false
        }, 2300)
    }

    // Init Ui Market Activity
    private fun initUi() {

        getNewsFromApi()
        getCoinsFromApi()

    }

    // Put Data To Shared
    private fun putData() {
        // Data News
        binding.newsMarket.txtNews.text = sharedPreferences.getString("news", "")
        binding.newsMarket.imgNews.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(sharedPreferences.getString("uriNews", ""))
                )
            )
        }
        // Data Coins


    }

    // Save Data
    private fun dataShared() {
        // First Run
        editor.putBoolean("isFirst", false)

        // News
        val random = (1..49).random()
        editor.putString("news", dataNews[random].first)
        editor.putString("uriNews", dataNews[random].second)

        // Coin


        editor.apply()
    }


    // News UI
    // Get News Data From ApiManager
    private fun getNewsFromApi() {

        apiManager.getNews(object : ApiManager.ApiCallBack<ArrayList<Pair<String, String>>> {
            override fun onSuccess(data: ArrayList<Pair<String, String>>) {
                // Put News Data To lateinit var
                dataNews = data
                // Call dataShared() And Work Him
                dataShared()
                // Call refresh() Put Data To Views
                refresh()
            }

            override fun onError(errorMassage: String) {
                Log.v(ERROR_HANDELER, "fun getNewsFromApi ==>> $errorMassage")
            }

        })
    }

    // Put Data From Api In Views
    private fun refresh() {
        val random = (1..49).random()
        binding.newsMarket.txtNews.text = dataNews[random].first
        binding.newsMarket.txtNews.setOnClickListener {
            refresh()
        }

        binding.newsMarket.imgNews.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(dataNews[random].second)))
        }
    }

    // Coins UI
    // Get Coins Data From ApiManager
    private fun getCoinsFromApi() {

        apiManager.getCionsList(object : ApiManager.ApiCallBack<List<CoinsData.Data>> {
            override fun onSuccess(data: List<CoinsData.Data>) {
                // Call And Send Data To showRecycler()
                showRecycler(data)
            }

            override fun onError(errorMassage: String) {
                Log.v("HandelerGetCoinsList", errorMassage)
            }

        })
    }

    // Put Data Coins To Adapter And Set Adapter & Layout Manager For Recycler View
    private fun showRecycler(data: List<CoinsData.Data>) {

        val adapter = MarketAdapter(data, this)
        binding.resMarket.recyclerItemMarket.adapter = adapter
        binding.resMarket.recyclerItemMarket.layoutManager = LinearLayoutManager(this)
    }

    // Start Activity CoinDetails And Set Coin Data
    override fun onToch(data: CoinsData.Data) {
        val intent = Intent(this, CoinDataActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("bundle1", data)
        bundle.putParcelable("bundle2", aboutDataMap[data.coinInfo.name] ?: CoinsAboutItem())
        intent.putExtra("dataOmade", bundle)
        startActivity(intent)
    }

    // Get Data From Adapter CoinData
    fun getAboutDataFromAssets() {

        val fileInString = applicationContext.assets
            .open("currencyinfo.json")
            .bufferedReader()
            .use { it.readText() }

        aboutDataMap = mutableMapOf()

        val gson = Gson()
        val dataAboutAll = gson.fromJson(fileInString, CoinsAboutData::class.java)

        dataAboutAll.forEach {
            aboutDataMap[it.currencyName!!] = CoinsAboutItem(
                it.info?.web,
                it.info?.github,
                it.info?.twt,
                it.info?.desc,
                it.info?.reddit,
            )
        }
    }
}