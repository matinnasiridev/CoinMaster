package ir.groid.coinmaster.apiManager


import ir.groid.coinmaster.apiManager.model.ChartData
import ir.groid.coinmaster.apiManager.model.CoinsData
import ir.groid.coinmaster.apiManager.model.NewsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiManager {
    private val apiService: ApiService

    init {

        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

    }

    fun getNews(apiCallBack: ApiCallBack<ArrayList<Pair<String, String>>>) {

        apiService.getTopNews().enqueue(object : Callback<NewsData> {
            override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {
                val data = response.body()!!
                val dataToSend: ArrayList<Pair<String, String>> = arrayListOf()
                data.data.forEach {
                    dataToSend.add(Pair(it.title, it.url))
                }
                apiCallBack.onSuccess(dataToSend)
            }

            override fun onFailure(call: Call<NewsData>, t: Throwable) {
                apiCallBack.onError(t.message!!)
            }

        })
    }

    fun getCionsList(apiCallBack: ApiCallBack<List<CoinsData.Data>>) {

        apiService.getTopCoins().enqueue(object : Callback<CoinsData> {
            override fun onResponse(call: Call<CoinsData>, response: Response<CoinsData>) {
                val data = response.body()!!
                apiCallBack.onSuccess(data.data)
            }

            override fun onFailure(call: Call<CoinsData>, t: Throwable) {
                apiCallBack.onError(t.message!!)
            }

        })
    }

    fun getChartData(
        period: String,
        symol: String,
        apiCallBack: ApiCallBack<ChartData>
    ) {
        var histoPer = ""
        var limit = 30
        var aggregate = 1

        when (period) {
            HOUR -> {
                histoPer = HISTO_MINUTE
                limit = 24
            }
            HOURS24 -> {
                histoPer = HISTO_HOUR
                limit = 60
                aggregate = 12
            }
            WEEK -> {
                histoPer = HISTO_HOUR
                aggregate = 6

            }
            MONTH -> {
                histoPer = HISTO_DAY
                limit = 30
            }
            MONTH3 -> {
                histoPer = HISTO_DAY
                limit = 90

            }
            YEAR -> {
                histoPer = HISTO_DAY
                aggregate = 13

            }
            ALL -> {
                histoPer = HISTO_DAY
                limit = 2000
                aggregate = 30

            }
        }

        apiService.getChartData(
            histoPer,
            symol,
            limit,
            aggregate
        ).enqueue(object : Callback<ChartData> {
            override fun onResponse(call: Call<ChartData>, response: Response<ChartData>) {
                apiCallBack.onSuccess(response.body()!!)
            }

            override fun onFailure(call: Call<ChartData>, t: Throwable) {
                apiCallBack.onError(t.message!!)
            }

        })
    }
    interface ApiCallBack<T> {
        fun onSuccess(data: T)
        fun onError(errorMassage: String)
    }
}
