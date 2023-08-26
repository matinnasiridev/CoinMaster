package ir.groid.coinmaster.di

import android.widget.ImageView


interface AppService {


    interface ImageLoader {
        fun loader(url: String, view: ImageView)
    }
}