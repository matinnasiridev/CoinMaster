package ir.groid.coinmaster.viewModels


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import ir.groid.coinmaster.model.RCoinAbout
import ir.groid.coinmaster.model.RCoinData
import ir.groid.coinmaster.model.RNewsData
import ir.groid.coinmaster.repository.AppRepository
import ir.groid.coinmaster.responce.CoinsAboutData
import ir.groid.coinmaster.util.NetworkChecker

class MarketVM(
    private val repository: AppRepository,
    private val netStatus: NetworkChecker
) : ViewModel() {
    private val dis = CompositeDisposable()
    fun getAllNews(): LiveData<List<RNewsData>> = repository.getAllNews()

    fun refreshNews(): Completable = repository.refreshNews()

    fun getAllCoins(): LiveData<List<RCoinData>> = repository.getAllCoins()

    fun shimmerStatus(): BehaviorSubject<Boolean> = repository.shimmerSubject

    fun refreshCoins(): Completable = repository.refreshCoins()

    private fun provideBroadcastReceiver(job: (Context?, Intent?) -> Unit): BroadcastReceiver {
        return object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                job(context, intent)
            }
        }
    }

    private fun internetConnection(
        context: Context,
        onChangeListener: (Context?, Intent?) -> Unit
    ): Intent? {
        return context.registerReceiver(
            provideBroadcastReceiver(onChangeListener),
            IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        )
    }

    fun internetConnected(c: Context, online: () -> Unit) {
        internetConnection(c) { _, _ ->
            if (netStatus.isInternetConnected)
                online()
        }
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