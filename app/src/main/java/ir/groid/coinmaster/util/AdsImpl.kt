package ir.groid.coinmaster.util

import android.app.Activity
import android.util.Log
import android.view.ViewGroup
import ir.groid.coinmaster.di.AppService
import ir.groid.coinmaster.util.Constans.TAP
import ir.tapsell.plus.AdRequestCallback
import ir.tapsell.plus.AdShowListener
import ir.tapsell.plus.TapsellPlus
import ir.tapsell.plus.TapsellPlusBannerType
import ir.tapsell.plus.TapsellPlusInitListener
import ir.tapsell.plus.model.AdNetworkError
import ir.tapsell.plus.model.AdNetworks
import ir.tapsell.plus.model.TapsellPlusAdModel
import ir.tapsell.plus.model.TapsellPlusErrorModel

class AdsImpl : AppService.AdsSystem {
    private lateinit var acc: Activity
    override fun init(activity: Activity, key: String, initSuccess: (AdNetworks?) -> Unit) {
        this.acc = activity
        TapsellPlus.initialize(acc, key, object : TapsellPlusInitListener {
            override fun onInitializeSuccess(p0: AdNetworks?) {
                Log.d(TAP, "Init Success: ${p0?.name}")
                initSuccess(p0)
            }

            override fun onInitializeFailed(p0: AdNetworks?, p1: AdNetworkError?) {
                Log.e(TAP, "Init Hase A Err ${p0?.name} And Message: ${p1?.errorMessage}")
            }

        })

    }

    override fun requestStandardBanner(
        key: String,
        type: TapsellPlusBannerType,
        onResponse: (String?) -> Unit
    ) {
        TapsellPlus.requestStandardBannerAd(acc, key, type, object : AdRequestCallback() {

            override fun response(p0: TapsellPlusAdModel?) {
                super.response(p0)
                Log.d(TAP, "Response ${p0?.responseId}")
                onResponse(p0?.responseId)
            }

            override fun error(p0: String?) {
                super.error(p0)
                Log.e(TAP, p0!!)
            }
        })
    }

    override fun showStandardBanner(responseId: String?, standardBanner: ViewGroup) {
        TapsellPlus.showStandardBannerAd(
            acc,
            responseId,
            standardBanner,
            object : AdShowListener() {
                override fun onOpened(p0: TapsellPlusAdModel?) {
                    super.onOpened(p0)
                    Log.d(TAP, "Ad Open!")
                }

                override fun onError(p0: TapsellPlusErrorModel?) {
                    super.onError(p0)
                    Log.e(TAP, p0!!.errorMessage)
                }
            })
    }

    override fun destroyStandardBanner(responseId: String?, standardBanner: ViewGroup) {
        TapsellPlus.destroyStandardBanner(acc, responseId, standardBanner)
    }
}