package ir.groid.coinmaster.di

import android.app.Activity
import android.view.ViewGroup
import android.widget.ImageView
import ir.tapsell.plus.TapsellPlusBannerType
import ir.tapsell.plus.model.AdNetworks


interface AppService {


    interface ImageLoader {
        fun loader(url: String, view: ImageView)
    }

    interface AdsSystem {
        fun init(activity: Activity, key: String, initSuccess: (AdNetworks?) -> Unit)

        fun requestStandardBanner(
            key: String,
            type: TapsellPlusBannerType = TapsellPlusBannerType.BANNER_320x50,
            onResponse: (String?) -> Unit
        )

        fun showStandardBanner(responseId: String?, standardBanner: ViewGroup)

        fun destroyStandardBanner(responseId: String?, standardBanner: ViewGroup)
    }

}