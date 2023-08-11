package ir.groid.coinmaster.di

import android.widget.ImageView

interface ImageLoader {

    fun load(url: String, imgView: ImageView)
}