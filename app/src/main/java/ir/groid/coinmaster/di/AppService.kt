package ir.groid.coinmaster.di

import android.widget.ImageView
import ir.groid.coinmaster.util.InitCallBack


interface AppService {


    interface ImageLoader {
        fun loader(url: String, view: ImageView)
    }

    interface AdsSystem {
        fun init(key: String, event: InitCallBack)
    }

}