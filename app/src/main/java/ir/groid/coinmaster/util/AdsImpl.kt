package ir.groid.coinmaster.util

import android.content.Context
import ir.groid.coinmaster.di.AppService
import ir.tapsell.plus.TapsellPlus
import ir.tapsell.plus.TapsellPlusInitListener
import ir.tapsell.plus.model.AdNetworkError
import ir.tapsell.plus.model.AdNetworks

class AdsImpl(private val context: Context) : AppService.AdsSystem {
    override fun init(key: String, event: InitCallBack) {
        TapsellPlus.initialize(context, key, object : TapsellPlusInitListener {
            override fun onInitializeSuccess(p0: AdNetworks?) {
                event.succ(p0)
            }

            override fun onInitializeFailed(p0: AdNetworks?, p1: AdNetworkError?) {
                event.err(p0, p1)
            }

        })

    }
}

interface InitCallBack {
    fun succ(p0: AdNetworks?)
    fun err(p0: AdNetworks?, p1: AdNetworkError?)
}