package ir.groid.coinmaster.apiManager


import ir.groid.coinmaster.apiManager.model.ChartData
import ir.groid.coinmaster.apiManager.model.CoinsData
import ir.groid.coinmaster.apiManager.model.NewsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers(API_KEYS)
    @GET("v2/news/")
    fun getTopNews(
        @Query("sortOrder") sortOrder: String = "popular"
    ): Call<NewsData>


    @Headers(API_KEYS)
    @GET("top/totalvolfull")
    fun getTopCoins(
        @Query("tsym") ts_ym: String = "USD"
    ): Call<CoinsData>


    @Headers(API_KEYS)
    @GET("{period}")
    fun getChartData(
        @Path("period") period: String,
        @Query("fsym") fromSymbol: String,
        @Query("limit") limit: Int,
        @Query("aggregate") aggregate: Int,
        @Query("tsym") toSymbol: String = "USD"
    ):Call<ChartData>


}