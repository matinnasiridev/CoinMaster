package ir.groid.coinmaster.di

import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideImageLoader : ImageLoader {

    override fun load(url: String, imgView: ImageView) {
        Glide
            .with(imgView.context)
            .load(url)
            .into(imgView)
    }
}