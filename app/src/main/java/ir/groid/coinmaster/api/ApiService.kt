package ir.groid.coinmaster.api


import io.reactivex.Single
import ir.groid.coinmaster.responce.ChartData
import ir.groid.coinmaster.responce.CoinsData
import ir.groid.coinmaster.responce.NewsData
import ir.groid.coinmaster.util.Constans
import ir.groid.coinmaster.util.Constans.API_KEYS
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers(API_KEYS)
    @GET("v2/news/")
    fun getTopNews(
        @Query("sortOrder") sortOrder: String = "popular"
    ): Single<NewsData>


    @Headers(API_KEYS)
    @GET("top/totalvolfull")
    fun getTopCoins(
        @Query("tsym") ts_ym: String = "USD"
    ): Single<CoinsData>


    @Headers(API_KEYS)
    @GET("{period}")
    fun getChartData(
        @Path("period") period: String,
        @Query("fsym") fromSymbol: String,
        @Query("limit") limit: Int,
        @Query("aggregate") aggregate: Int,
        @Query("tsym") toSymbol: String = "USD"
    ): Single<ChartData>

}

fun provideApiService(): ApiService {
    return Retrofit
        .Builder()
        .baseUrl(Constans.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ApiService::class.java)
}