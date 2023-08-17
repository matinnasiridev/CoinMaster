package ir.groid.coinmaster.util

import android.app.Application
import com.adivery.sdk.Adivery
import com.adivery.sdk.AdiveryListener
import ir.groid.coinmaster.di.AppService

class AdsSystem(private val application: Application) : AppService.AdsService {
    private val keyAd = "b0c76e98-80f0-4d30-9efa-8a43d5520d65"
    private val keyAdT = "42624be1-db43-4264-a218-8a5ab64c70d7"
    private val appID = "57b20b85-8113-44e4-8bb3-4fa483e1e44f"

    override fun initAdd(appID: String, addID: String) {
        Adivery.configure(application, appID)
        Adivery.prepareInterstitialAd(application.applicationContext, addID)

    }

    override fun showAds() {
        if (Adivery.isLoaded(keyAd)) {
            Adivery.showAd(keyAd)
        }
    }

    override fun showNative() {
        if (Adivery.isLoaded(keyAdT)) {
            Adivery.showAd(keyAdT)
        }
    }

    override fun listener(listener: AdiveryListener) {
        Adivery.addGlobalListener(listener)
    }
}