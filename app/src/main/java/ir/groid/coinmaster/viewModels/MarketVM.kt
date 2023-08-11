package ir.groid.coinmaster.viewModels


import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import ir.groid.coinmaster.di.ImageLoader
import ir.groid.coinmaster.model.RCoinAbout
import ir.groid.coinmaster.model.RCoinData
import ir.groid.coinmaster.model.RNewsData
import ir.groid.coinmaster.repository.AppRepository
import ir.groid.coinmaster.responce.CoinsAboutData

class MarketVM(
    private val repository: AppRepository,
    private val imageLoader: ImageLoader
) : ViewModel() {
    private val dis = CompositeDisposable()
    fun getAllNews(): LiveData<List<RNewsData>> = repository.getAllNews()

    fun refreshNews(): Completable = repository.refreshNews()

    fun getAllCoins(): LiveData<List<RCoinData>> = repository.getAllCoins()

    fun progressStatus(): BehaviorSubject<Boolean> = repository.progressBarSubject

    fun refreshCoins(): Completable = repository.refreshCoins()

    fun ImageView.load(url: String) {
        imageLoader.load(url, this)
    }

    fun getStatistics(coinName: String){

    }

    fun getAboutDataByName(c: Context, coinName: String): RCoinAbout {
        val file = c.assets
            .open("currencyinfo.json")
            .bufferedReader()
            .use { it.readText() }

        val gson = Gson()
        val response = gson.fromJson(file, CoinsAboutData::class.java)
        var about = RCoinAbout()

        response.forEach {
            if (it.currencyName == coinName) {
                about = RCoinAbout(
                    coinWebsite = it.info!!.web,
                    coinGithub = it.info.github,
                    coinTwitter = it.info.twt,
                    coinDes = it.info.desc,
                    coinReddit = it.info.reddit
                )
            }
        }
        return about
    }

    fun addDis(d: Disposable) = dis.add(d)

    override fun onCleared() {
        super.onCleared()
        dis.dispose()
    }
}