package ir.groid.coinmaster.di

import com.adivery.sdk.AdiveryListener


interface AppService {


    interface AdsService {
        fun initAdd(appID: String, addID: String)
        fun listener(listener: AdiveryListener)
        fun showAds()
        fun showNative()
    }
}