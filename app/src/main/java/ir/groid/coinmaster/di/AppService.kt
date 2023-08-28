package ir.groid.coinmaster.di

import android.app.Activity
import android.view.ViewGroup
import android.widget.ImageView
import ir.tapsell.plus.TapsellPlusBannerType


interface AppService {


    interface ImageLoader {
        fun loader(url: String, view: ImageView)
    }

    interface AdsSystem {

        fun standardBanner(
            acc: Activity,
            keyTwo: String,
            standardBanner: ViewGroup
        ) {
            requestStandardBanner(keyTwo) {
                showStandardBanner(it, standardBanner)
            }
        }

        fun init(activity: Activity, key: String, initSuccess: () -> Unit)

        fun requestStandardBanner(
            key: String,
            type: TapsellPlusBannerType = TapsellPlusBannerType.BANNER_320x50,
            onResponse: (String?) -> Unit
        )

        fun showStandardBanner(responseId: String?, standardBanner: ViewGroup)

        fun destroyStandardBanner(responseId: String?, standardBanner: ViewGroup)
    }

}